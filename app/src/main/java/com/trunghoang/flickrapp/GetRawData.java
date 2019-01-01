package com.trunghoang.flickrapp;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

enum DownloadStatus { IDLE, PROCESSING, NOT_INITIALIZED, FAILED_OR_EMPTY, OK };

class GetRawData extends AsyncTask<String, Void, String> {

    private DownloadStatus downloadStatus;

    interface OnDownloadCompleteListener {
        void onDownloadComplete(String result, DownloadStatus status);
    }

    private OnDownloadCompleteListener mOnDownloadCompleteListener;

    GetRawData(OnDownloadCompleteListener onDownloadCompleteListener) {
        this.downloadStatus = DownloadStatus.IDLE;
        this.mOnDownloadCompleteListener = onDownloadCompleteListener;
    }

    void runInTheSameThread(String... strings) {
        onPostExecute(doInBackground(strings[0]));
    }

    @Override
    protected String doInBackground(String... strings) {

        HttpURLConnection connection = null;
        BufferedReader bufferedReader = null;

        if (strings[0] == null) {
            this.downloadStatus = DownloadStatus.NOT_INITIALIZED;
            return null;
        }

        try {
            downloadStatus = DownloadStatus.PROCESSING;

            URL url = new URL(strings[0]);

            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();

            bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder stringBuilder = new StringBuilder();
            String line;

            while ( null != (line = bufferedReader.readLine())) {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }

            downloadStatus = DownloadStatus.OK;

            return stringBuilder.toString();
        } catch (IOException e) {
            downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
            e.printStackTrace();
        } finally {
            if (connection != null) {
                connection.disconnect();
            }
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }

    @Override
    protected void onPostExecute(String result) {
        mOnDownloadCompleteListener.onDownloadComplete(result, downloadStatus);
    }
}
