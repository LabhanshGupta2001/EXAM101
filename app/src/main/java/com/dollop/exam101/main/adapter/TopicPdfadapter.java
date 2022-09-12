package com.dollop.exam101.main.adapter;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.dollop.exam101.Basics.Database.PdfVideoTable;
import com.dollop.exam101.Basics.Database.UserDataHelper;
import com.dollop.exam101.Basics.Retrofit.Const;
import com.dollop.exam101.Basics.UtilityTools.Constants;
import com.dollop.exam101.Basics.UtilityTools.Utils;
import com.dollop.exam101.databinding.ItemTopicPdfBinding;
import com.dollop.exam101.main.activity.PdfViewActivity;
import com.dollop.exam101.main.model.TopicDetailModel;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class TopicPdfadapter extends RecyclerView.Adapter<TopicPdfadapter.MyViewHolder> {
    TopicDetailModel topicDetailModel;
    Context context;
    File fileDownLoad = null;
    private String fileName;
    private Bundle bundle2;
    private ProgressDialog pDialog;

    public TopicPdfadapter(TopicDetailModel topicDetailModel, Context context, String pdf, Bundle bundle) {
        this.topicDetailModel = topicDetailModel;
        this.context = context;
        this.bundle2 = bundle;
    }

    @NonNull

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemTopicPdfBinding binding = ItemTopicPdfBinding.inflate(LayoutInflater.from(context));
        return new MyViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        String pdf = topicDetailModel.pdfFile.get(position);
        holder.binding.tvUpload.setText(pdf);
        holder.binding.cvPdf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String fileName = pdf.substring(pdf.lastIndexOf('/') + 1);
                //  fileDownLoad = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS), fileName);
                fileDownLoad = new File(context.getFilesDir().getAbsolutePath(), fileName);
                Date c = Calendar.getInstance().getTime();
                System.out.println("Current time => " + c);
                SimpleDateFormat df = new SimpleDateFormat("dd MMM yyyy", Locale.getDefault());
                String formattedDate = df.format(c);
                Bundle bundle = new Bundle();

                if (fileDownLoad.exists()) {
                    bundle.putString(Constants.Key.pdf, fileDownLoad.toString());
                    bundle.putString(Constants.Key.From, Constants.Key.Document);
                    Utils.E("fileDownLoad::::::" + fileDownLoad);
                    //  OpenPDFFile();
                    Utils.I(context, PdfViewActivity.class, bundle);

                } else {
                    bundle.putString(Constants.Key.pdf, Const.Url.HOST_URL + pdf);
                    bundle.putString(Constants.Key.From, Constants.Key.urlType);
                    try {
                        //Adding data in DataBase
                        storeInDB(fileDownLoad.toString(), formattedDate);
                        fileDownLoad.createNewFile();
                        new DownloadFileFromURL().execute(Const.Url.HOST_URL + pdf);
                        Utils.E("Downloading::::" + Const.Url.HOST_URL + pdf);

                    } catch (Exception e) {
                        e.printStackTrace();
                        Utils.E("Errorsss:::::" + e.getMessage());
                    }
                    Utils.I(context, PdfViewActivity.class, bundle);

                }
            }
        });

    }


    @Override
    public int getItemCount() {

        return topicDetailModel.pdfFile.size();

    }

    protected Dialog onCreateDialog() {
        pDialog = new ProgressDialog(context);
        pDialog.setMessage("Downloading file. Please wait...");
        pDialog.setIndeterminate(false);
        pDialog.setMax(100);
        pDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        pDialog.setCancelable(true);
        pDialog.show();
        return pDialog;
    }

    void storeInDB(String fileName, String date) {
        PdfVideoTable pdfData = new PdfVideoTable();
        pdfData.orderExamUUID = bundle2.getString(Constants.Key.orderExamUuid);
        pdfData.topicUUID = bundle2.getString(Constants.Key.topicUuid);
        pdfData.topicName = bundle2.getString(Constants.Key.topicName);
        pdfData.topicDescription = bundle2.getString(Constants.Key.topicDetail);
        pdfData.pdfPath = fileName;
        pdfData.date = date;
        UserDataHelper.getInstance().insertPdfData(pdfData);


    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        final ItemTopicPdfBinding binding;

        public MyViewHolder(@NonNull ItemTopicPdfBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }

    class DownloadFileFromURL extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Bar Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
          //  onCreateDialog();
        }

        /**
         * Downloading file in background thread
         */
        @Override
        protected String doInBackground(String... f_url) {
            int count;
            try {

                URL url = new URL(f_url[0]);
                URLConnection connection = url.openConnection();
                connection.connect();

                // this will be useful so that you can show a tipical 0-100%
                // progress bar
                int lenghtOfFile = connection.getContentLength();

                // download the file
                InputStream input = new BufferedInputStream(url.openStream(),
                        8192);

                // Output stream
                //Date date=new Date();
                String fileName = f_url[0].substring(f_url[0].lastIndexOf('/') + 1);


                OutputStream output = new FileOutputStream(fileDownLoad);

                byte data[] = new byte[1024];

                long total = 0;

                while ((count = input.read(data)) != -1) {
                    total += count;
                    // publishing the progress....
                    // After this onProgressUpdate will be called
                    publishProgress("" + (int) ((total * 100) / lenghtOfFile));

                    // writing data to file
                    output.write(data, 0, count);
                }

                // flushing output
                output.flush();

                // closing streams
                output.close();
                input.close();
                JSONObject jsonObject = new JSONObject("");

            } catch (JSONException e) {
                Log.e("Error: ", e.getMessage());
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        /**
         * Updating progress bar
         */
        protected void onProgressUpdate(String... progress) {
            // setting progress percentage
           // pDialog.setProgress(Integer.parseInt(progress[0]));
        }

        /**
         * After completing background task Dismiss the progress dialog
         **/
        @Override
        protected void onPostExecute(String file_url) {
            // dismiss the dialog after the file was downloaded
             Utils.E("Post Executed ::: " + file_url);
         //   pDialog.dismiss();

        }

    }

}
