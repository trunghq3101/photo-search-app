package com.trunghoang.flickrapp;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;

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
        recyclerView.setLayoutManager(new GridLayoutManager(this, 3));
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (searchManager != null && searchView != null) {
            SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
            searchView.setSearchableInfo(searchableInfo);
            searchView.setIconifiedByDefault(false);
        }
        return true;
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
