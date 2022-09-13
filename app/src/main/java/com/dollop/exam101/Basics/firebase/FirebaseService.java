package com.dollop.exam101.Basics.firebase;

import android.annotation.SuppressLint;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.R;
import com.dollop.exam101.Basics.Retrofit.APIError;
import com.dollop.exam101.Basics.Retrofit.ApiService;
import com.dollop.exam101.Basics.Retrofit.RetrofitClient;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.Basics.UtilityTools.SavedData;
import com.dollop.exam101.main.activity.DashboardScreenActivity;
import com.dollop.exam101.main.model.AllResponseModel;
import com.google.firebase.FirebaseApp;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.google.gson.Gson;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Random;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created By Anil
 */
public class FirebaseService extends FirebaseMessagingService {
    Service activity = FirebaseService.this;
    String NOTIFICATION_CHANNEL_ID = "dollop.exam101" + System.currentTimeMillis();
    int m;
    String notificationTitle = Constants.Key.blank;
    String messageBody = Constants.Key.blank;
    Class<?> activityClass = null;
    private String action;
    private String cashBack = "";
    private String availableBalance = "";

    public static void GenerateToken(final Context context) {
        FirebaseApp.initializeApp(context);
        FirebaseMessaging.getInstance().setAutoInitEnabled(true);
        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Utils.E("getInstanceId failed" + task.getException());
                        return;
                    }
                    String token = task.getResult();
                    Utils.E("FCMtoken>>:::: " + token);
                    SavedData.saveFirebaseToken(token);
                    if (Utils.IS_LOGIN()) {
                        sendRegistrationToServer(token, context);
                    }
                });
    }

    public static void sendRegistrationToServer(final String token, final Context activity) {
        RetrofitClient.getClient().UpdateFirebaseToken(Utils.GetSession().token, token)
                .enqueue(new Callback<AllResponseModel>() {
                    @Override
                    public void onResponse(@NotNull Call<AllResponseModel> call, @NotNull Response<AllResponseModel> response) {
                        try {
                            if (response.isSuccessful()) {
                                Utils.E("Update Successfully");
                            } else {
                                assert response.errorBody() != null;
                                APIError apiError = new Gson().fromJson(response.errorBody().charStream(), APIError.class);
                                Utils.T(activity, apiError.message);
                                if (response.code() == Constants.Key.Unauthorized) {
                                    Utils.UnAuthorizationToken(activity);
                                }
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<AllResponseModel> call, @NotNull Throwable t) {

                    }
                });
    }

    private void handleDataMessage(JSONObject json) {
        try {
            JSONObject data = json.getJSONObject(Constants.Key.data);
            action = data.getString(Constants.Key.action);

            JSONObject payload;
            if (!data.getString(Constants.Key.payload).equals("")) {
                payload = new JSONObject(data.getString(Constants.Key.payload));
                cashBack = payload.getString("cash_back");
                availableBalance = payload.getString("wallet");
            }

            Utils.E("action:" + action);
            // if()

            m = new Random().nextInt();
            if (!NotificationUtils.isAppIsInBackground(getApplicationContext())) {
                Intent pushNotification = new Intent(Config.PUSH_NOTIFICATION);
                pushNotification.putExtra(Constants.Key.action, action);
                pushNotification.putExtra(Constants.Key.title, data.getString(Constants.Key.title));
                pushNotification.putExtra(Constants.Key.message, data.getString(Constants.Key.message));
                LocalBroadcastManager.getInstance(this).sendBroadcast(pushNotification);
            }
            sendNotification(json);

        } catch (Exception e) {
            Utils.E("Exception: " + e.getMessage());
        }
    }

    @Override
    public void onMessageReceived(@NotNull RemoteMessage remoteMessage) {
        Utils.E("From: " + remoteMessage.getFrom());

        if (remoteMessage.getNotification() != null) {
            Utils.E("From:notification:: " + remoteMessage.getNotification());
            Utils.E("Notification Body: " + remoteMessage.getNotification().getBody());
            JSONObject jsonObject = new JSONObject();
            try {
                jsonObject.put("title", remoteMessage.getNotification().getTitle());
                jsonObject.put("message", remoteMessage.getNotification().getBody());
                //Testing Put this i

            } catch (JSONException e) {
                e.printStackTrace();
            }
            sendNotification(jsonObject);
        }
        if (remoteMessage.getData().size() > 0) {
            Utils.E("Data Payload: " + remoteMessage.getData());
            try {
                JSONObject json = new JSONObject(remoteMessage.getData().toString());
                handleDataMessage(json);
            } catch (Exception e) {
                Utils.E("Exception: " + e.getMessage());
            }

        }

    }

    @Override
    public void onNewToken(@NonNull String token) {
        Utils.E("Refreshed token: service : " + token);
        SavedData.saveFirebaseToken(token);
        if (Utils.IS_LOGIN()) {
            sendRegistrationToServer(token, activity);
        }

    }

    @SuppressLint("UnspecifiedImmutableFlag")
    private void sendNotification(@NotNull JSONObject jsonObject) {

        try {
            //Testing
             notificationTitle = jsonObject.getString("title");
             messageBody = jsonObject.getString("message");

            //Live
            Bundle bundle = new Bundle();
          //  JSONObject data = jsonObject.getJSONObject(Constants.Key.data);
          //  action = data.getString(Constants.Key.action);
           // notificationTitle = data.getString(Constants.Key.title);
          //  messageBody = data.getString(Constants.Key.message);
           // bundle.putString(Constants.Key.action, action);
       //     bundle.putString(Constants.Key.title, data.getString(Constants.Key.title));
        //    bundle.putString(Constants.Key.message, data.getString(Constants.Key.message));
            activityClass = DashboardScreenActivity.class;
            Intent intent = new Intent(this, activityClass);
            intent.putExtras(bundle);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            PendingIntent pendingIntent;
            pendingIntent = PendingIntent.getActivity(this, 123, intent, PendingIntent.FLAG_UPDATE_CURRENT);
            Uri defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            NotificationCompat.Builder notificationBuilder =
                    new NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_ID)
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setContentTitle(notificationTitle)
                            .setContentText(messageBody)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(messageBody))
                            .setAutoCancel(true)
                            .setBadgeIconType(NotificationCompat.BADGE_ICON_SMALL)
                            .setColor(ContextCompat.getColor(activity, R.color.theme))
                            .setSound(defaultSoundUri)
                            .setContentIntent(pendingIntent);

            NotificationManager notificationManager =
                    (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
            // Since android Oreo notification channel is needed.
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID,
                        NOTIFICATION_CHANNEL_ID,
                        NotificationManager.IMPORTANCE_DEFAULT);
                notificationManager.createNotificationChannel(channel);
            }
            notificationManager.notify(m, notificationBuilder.build());

        } catch (Exception e) {
            Utils.E("JSONException::" + e);
            e.printStackTrace();
        }

    }
}

