package com.dollop.exam101.main.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Dialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.Basics.vimeoplayer.model.vimeo.VimeoResponse;
import com.dollop.exam101.Basics.vimeoplayer.network.VimeoClientAPI;
import com.dollop.exam101.Basics.vimeoplayer.network.VimeoInterface;
import com.dollop.exam101.databinding.ActivityVideoViewBinding;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.util.Util;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

import retrofit2.Call;
import retrofit2.Response;

public class VideoViewActivity extends AppCompatActivity implements View.OnClickListener {
    String VIMDEO_ID = "";
    Activity activity;
    ActivityVideoViewBinding binding;
    Bundle bundle;
    private SimpleExoPlayer player;

    //Release references
    private boolean playWhenReady = false; //If true the player auto play the media
    private int currentWindow = 0;
    private long playbackPosition = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityVideoViewBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        activity = VideoViewActivity.this;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //  binding.llToolbar.setVisibility(View.GONE);
        } else {
            // binding.llToolbar.setVisibility(View.VISIBLE);

        }
        init();
    }

    void init() {
        bundle = getIntent().getExtras();
        binding.ivBack.setOnClickListener(this);

        if (bundle.getString(Constants.Key.urlType).equals(Constants.Key.video)) {
            if (bundle.getString(Constants.Key.For).equals(Constants.Key.vimeoLink)) {
                VIMDEO_ID = bundle.getString(Constants.Key.videoUrl);
                VIMDEO_ID = VIMDEO_ID.substring(VIMDEO_ID.lastIndexOf("/") + 1, VIMDEO_ID.length());
                binding.pdfView.setVisibility(View.GONE);
                binding.videoView.setVisibility(View.VISIBLE);


            } else if (bundle.getString(Constants.Key.For).equals(Constants.Key.yTLink)) {
                binding.youtubePlayerView.setVisibility(View.VISIBLE);
                getLifecycle().addObserver(binding.youtubePlayerView);
                binding.youtubePlayerView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
                        String videoId = bundle.getString(Constants.Key.videoUrl);
                        videoId = videoId.substring(videoId.lastIndexOf("/") + 1, VIMDEO_ID.length());
                        youTubePlayer.loadVideo(videoId, 0);
                      //  youTubePlayer.pause();
                    }
                });
            }

        }
    }


    @Override
    public void onClick(View view) {
        if (view == binding.ivBack) {
            onBackPressed();

        }

    }

    private void createMediaItem(String url) {
        MediaItem mediaItem = MediaItem.fromUri(url);
        player.setMediaItem(mediaItem);
    }

    private void initializePlayer() {
        //To play streaming media, you need an ExoPlayer object.
        //SimpleExoPlayer is a convenient, all-purpose implementation of the ExoPlayer interface.
        player = new SimpleExoPlayer.Builder(this).build();
        binding.videoView.setPlayer(player);


        callVimeoAPIRequest();

        //Supply the state information you saved in releasePlayer to your player during initialization.
        player.setPlayWhenReady(playWhenReady);
        player.seekTo(currentWindow, playbackPosition);
        player.prepare();
    }

    private void callVimeoAPIRequest() {
        Dialog progressDialog = Utils.initProgressDialog(activity);

        VimeoInterface vimeoInterface = VimeoClientAPI.getClient().create(VimeoInterface.class);
        vimeoInterface.getVimeoUrlResponse(VIMDEO_ID)
                .enqueue(new retrofit2.Callback<VimeoResponse>() {
                    @Override
                    public void onResponse(@NotNull Call<VimeoResponse> call, @NotNull Response<VimeoResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            progressDialog.dismiss();
                            //Create media item
                            if (response.body().getRequest().getFiles().getProgressive().size() > 0)
                                createMediaItem(response.body().getRequest().getFiles().getProgressive().get(0).getUrl());

                            Log.d("TAG", response.body().getRequest().getFiles().getProgressive().get(0).getUrl());
                        }
                    }

                    @Override
                    public void onFailure(@NotNull Call<VimeoResponse> call, @NotNull Throwable t) {
                        Log.e("TAG", Objects.requireNonNull(t.getMessage()));

                    }
                });
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        binding.videoView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //  binding.llToolbar.setVisibility(View.GONE);
        } else {
            //  binding.llToolbar.setVisibility(View.VISIBLE);

        }
        if (Util.SDK_INT >= 24) {
            //Init exoplayer builder
            if (bundle.getString(Constants.Key.urlType).equals(Constants.Key.video)) {
                if (bundle.getString(Constants.Key.For).equals(Constants.Key.vimeoLink)) {
                    initializePlayer();
                }
            }
        }
    }

    /**
     * Android API level 24 and higher supports multiple windows.
     * As your app can be visible, but not active in split window mode, you need to initialize the player in onStart.
     * Android API level 24 and lower requires you to wait as long as possible until you grab resources,
     * so you wait until onResume before initializing the player.
     */
    @Override
    public void onResume() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            //    binding.llToolbar.setVisibility(View.GONE);
        } else {
            // binding.llToolbar.setVisibility(View.VISIBLE);

        }
        super.onResume();
        //Helper method which allows you to have a full-screen experience.
        hideSystemUi();
        if ((Util.SDK_INT < 24 || player == null)) {
            //Init exoplayer builder
            if (bundle.getString(Constants.Key.urlType).equals(Constants.Key.video)) {
                if (bundle.getString(Constants.Key.For).equals(Constants.Key.vimeoLink)) {
                    initializePlayer();
                }
            }
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT < 24) {
            //Frees the player's resources and destroys it.
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT >= 24) {
            //Frees the player's resources and destroys it.
            releasePlayer();
        }
    }

    private void releasePlayer() {
        if (player != null) {
            playWhenReady = player.getPlayWhenReady();
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            player.release();
            player = null;
        }
    }

}