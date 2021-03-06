package com.trunghoang.flickrapp;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.ViewGroup;

public class SearchResultActivity extends BaseActivity {

    private static final String TAG = "SearchResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        activateToolbar(true);
        Log.d(TAG, "onCreate: ");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        executeSearch(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        Log.d(TAG, "onCreateOptionsMenu: ");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        if (searchManager != null && searchView != null) {
            SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
            searchView.setSearchableInfo(searchableInfo);
            searchView.setIconifiedByDefault(false);
            if (((ViewGroup)findViewById(R.id.search_result_fragment_container)).getChildCount() == 0) {
                searchView.requestFocus();
            }
            restoreQuery(searchView);
        }
        return true;
    }

    private void executeSearch(Intent intent) {
        if (intent != null && Intent.ACTION_SEARCH.equals(intent.getAction())) {
            SearchResultFragment searchResultFragment = new SearchResultFragment();
            searchResultFragment.setArguments(intent.getExtras());

            FragmentTransaction fragmentTransaction = getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.search_result_fragment_container, searchResultFragment);

            if (((ViewGroup)findViewById(R.id.search_result_fragment_container)).getChildCount() != 0) {
                fragmentTransaction.addToBackStack(null);
            }
            fragmentTransaction.commit();
        }
    }

    private void restoreQuery(SearchView searchView) {
        SearchResultFragment searchResultFragment = (SearchResultFragment) getSupportFragmentManager().findFragmentById(R.id.search_result_fragment_container);
        if (searchResultFragment != null) {
            if (searchResultFragment.getArguments() != null) {
                searchView.setQuery(searchResultFragment.getArguments().getString(SearchManager.QUERY), false);
            }
        }
    }

}
