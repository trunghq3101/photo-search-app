package com.trunghoang.flickrapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

class GetFlickrJsonData extends AsyncTask<String, Void, ArrayList<Photo>> implements GetRawData.OnDownloadCompleteListener {

    private static final String TAG = "GetFlickrJsonData";

    interface OnDataAvailableListener {
        void onDataAvailable(ArrayList<Photo> data, DownloadStatus status);
    }

    private ArrayList<Photo> photos;
    private String baseUrl;
    private String language;
    private Boolean matchAll;
    private OnDataAvailableListener onDataAvailableListener;
    private Boolean runningInTheSameThread;

    GetFlickrJsonData(OnDataAvailableListener onDataAvailableListener, String baseUrl, String language, Boolean matchAll) {
        this.baseUrl = baseUrl;
        this.language = language;
        this.matchAll = matchAll;
        this.onDataAvailableListener = onDataAvailableListener;
        photos = new ArrayList<>();
        runningInTheSameThread = false;
    }

    @Override
    protected ArrayList<Photo> doInBackground(String... strings) {
        GetRawData getRawData = new GetRawData(this);
        getRawData.runInTheSameThread(createUri(strings[0]));
        return photos;
    }

    @Override
    protected void onPostExecute(ArrayList<Photo> photos) {
        if (onDataAvailableListener != null) {
            onDataAvailableListener.onDataAvailable(photos, DownloadStatus.OK);
        }
    }

    @Override
    protected void onCancelled(ArrayList<Photo> photos) {
        super.onCancelled(photos);
    }

    private String createUri(String searchCriteria) {
        return Uri.parse(baseUrl).buildUpon()
                .appendQueryParameter("tags", searchCriteria)
                .appendQueryParameter("tagmode", matchAll ? "all" : "any")
                .appendQueryParameter("lang", language)
                .appendQueryParameter("format", "json")
                .appendQueryParameter("nojsoncallback", "1")
                .build().toString();
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            try {
                JSONObject result = new JSONObject(data);
                JSONArray items = result.getJSONArray("items");
                for (int i = 0; i < items.length(); i++) {
                    JSONObject photoJSON = items.getJSONObject(i);
                    String photoUrl = photoJSON.getJSONObject("media").getString("m");
                    Photo photo = new Photo(
                            photoJSON.getString("title"),
                            photoJSON.getString("author"),
                            photoJSON.getString("author_id"),
                            photoJSON.getString("tags"),
                            photoUrl.replaceFirst("_m.", "_b."),
                            photoUrl
                    );
                    photos.add(photo);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                status = DownloadStatus.FAILED_OR_EMPTY;
            }
        } else {
            Log.d(TAG, "onDownloadComplete: " + status);
        }

        if (runningInTheSameThread && onDataAvailableListener != null) {
            onDataAvailableListener.onDataAvailable(photos, status);
        }
    }
}
