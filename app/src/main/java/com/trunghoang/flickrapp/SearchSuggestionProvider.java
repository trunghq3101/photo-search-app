package com.trunghoang.flickrapp;

import android.content.SearchRecentSuggestionsProvider;

public class SearchSuggestionProvider extends SearchRecentSuggestionsProvider {
    final static String AUTHORITY = "com.trunghoang.flickrapp";
    final static int MODE = DATABASE_MODE_QUERIES;
    public SearchSuggestionProvider() {
        setupSuggestions(AUTHORITY, MODE);
    }
}
