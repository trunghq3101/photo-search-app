package com.trunghoang.flickrapp;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.ViewGroup;

public class SearchResultActivity extends BaseActivity {

    private static final String TAG = "SearchResultActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);

        activateToolbar(true);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        executeSearch(intent);
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

}
