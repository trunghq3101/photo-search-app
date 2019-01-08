package com.trunghoang.flickrapp;

import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.util.Log;
import android.view.View;
import android.widget.SearchView;

public class MainActivity extends BaseActivity {

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d(TAG, "onCreate: ");

        setContentView(R.layout.activity_main);

        activateToolbar(false);
    }

    @Override
    protected void onResume() {
        super.onResume();
        ConstraintLayout layout = findViewById(R.id.content_main_layout);
        layout.requestFocus();
        SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
        SearchView searchView = findViewById(R.id.search_view);
        if (searchManager != null && searchView != null) {
            SearchableInfo searchableInfo = searchManager.getSearchableInfo(getComponentName());
            searchView.setSearchableInfo(searchableInfo);
            searchView.setIconifiedByDefault(false);
        }
    }

    public void searchClick(View view) {
        Intent intent = new Intent(this, SearchResultActivity.class);
        startActivity(intent);
    }
}
