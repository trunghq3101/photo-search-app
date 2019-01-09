package com.trunghoang.flickrapp;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.provider.SearchRecentSuggestions;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public class SearchResultFragment extends Fragment implements PhotoTouchListener.ItemTouchListener,
        GetFlickrJsonData.OnDataAvailableListener {
    private static final String TAG = "SearchResultFragment";
    private static final String QUERY = "query";
    private PhotoListAdapter photoListAdapter;
    private RecyclerView recyclerView;
    private SearchRecentSuggestions searchRecentSuggestions;
    private String query;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View layout = inflater.inflate(R.layout.search_result_fragment_layout, container, false);

        recyclerView = layout.findViewById(R.id.search_result_recycler_view);
        recyclerView.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        photoListAdapter = new PhotoListAdapter(new ArrayList<Photo>());
        recyclerView.setAdapter(photoListAdapter);
        PhotoTouchListener photoTouchListener = new PhotoTouchListener(getActivity(), recyclerView, this);
        recyclerView.addOnItemTouchListener(photoTouchListener);

        searchRecentSuggestions = new SearchRecentSuggestions(getActivity(),
                SearchSuggestionProvider.AUTHORITY,
                SearchSuggestionProvider.MODE);

        if (getArguments() != null) {
            query = getArguments().getString(SearchManager.QUERY);
            if (query != null) {
                searchRecentSuggestions.saveRecentQuery(query, null);
                GetFlickrJsonData getFlickrJsonData = new GetFlickrJsonData(this,
                        "https://api.flickr.com/services/feeds/photos_public.gne",
                        "en-US",
                        true);
                getFlickrJsonData.execute(query);
            }
        }

        return layout;
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        outState.putString(QUERY, query);
        super.onSaveInstanceState(outState);
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        if (savedInstanceState != null) {
            query = savedInstanceState.getString(QUERY);
        }
    }

    @Override
    public void onResume() {
        recyclerView.requestFocus();
        SearchView searchView;
        if (getActivity() != null) {
            searchView = getActivity().findViewById(R.id.action_search);
            searchView.setQuery(query, false);
        }
        super.onResume();
    }

    @Override
    public void onDataAvailable(ArrayList<Photo> data, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            photoListAdapter.updatePhotosData(data);
        }
    }

    @Override
    public void itemSingleTouch(int position) {
        Intent intent = new Intent(getActivity(), PhotoDetailActivity.class);
        intent.putExtra(BaseActivity.PHOTO_TRANSFER, photoListAdapter.getPhoto(position));
        startActivity(intent);
    }
}
