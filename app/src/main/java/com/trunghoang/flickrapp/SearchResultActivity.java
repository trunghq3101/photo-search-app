package com.trunghoang.flickrapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import java.util.ArrayList;

public class SearchResultActivity extends BaseActivity implements GetFlickrJsonData.OnDataAvailableListener,
        PhotoTouchListener.ItemTouchListener {

    private static final String TAG = "SearchResultActivity";
    private PhotoListAdapter photoListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        activateToolbar(true);

        Intent intent = getIntent();
        String query = "";

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            query = intent.getStringExtra(SearchManager.QUERY);
        }

        RecyclerView recyclerView = findViewById(R.id.photos_recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        photoListAdapter = new PhotoListAdapter(new ArrayList<Photo>());
        recyclerView.setAdapter(photoListAdapter);
        PhotoTouchListener photoTouchListener = new PhotoTouchListener(this, recyclerView, this);
        recyclerView.addOnItemTouchListener(photoTouchListener);

        GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this,
                "https://api.flickr.com/services/feeds/photos_public.gne",
                "en-US",
                true);
        getFlickrJsonData.execute(query);
    }

    @Override
    public void onDataAvailable(ArrayList<Photo> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            Log.d(TAG, "onDataAvailable: downloaded");
            photoListAdapter.updatePhotosData(data);
        }
    }

    @Override
    public void itemSingleTouch(int position) {
        Intent intent = new Intent(this, PhotoDetailActivity.class);
        intent.putExtra(PHOTO_TRANSFER, photoListAdapter.getPhoto(position));
        startActivity(intent);
    }
}
