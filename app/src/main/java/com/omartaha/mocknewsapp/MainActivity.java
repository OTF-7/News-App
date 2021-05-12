package com.omartaha.mocknewsapp;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity
        implements SharedPreferences.OnSharedPreferenceChangeListener, LoaderManager.LoaderCallbacks<List<News>> {
    private static final String GUARDIAN_REQUEST_URL =
            "https://content.guardianapis.com/search?";
    private static final String GUARDIAN_API_KEY = "36733e71-f401-4a64-bb6e-e9d3a5b2ab05";
    private static final int NEWS_LOADER_ID = 1;
    private TextView noFoundNews;
    private NewsAdapter newsAdapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        noFoundNews = findViewById(R.id.connection_failed);

        recyclerView = findViewById(R.id.news_recycler_view);
        newsAdapter = new NewsAdapter(this, new ArrayList<News>());
        recyclerView.setAdapter(newsAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        sharedPreferences.registerOnSharedPreferenceChangeListener(this);

        ConnectivityManager connectivityManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();

        if (networkInfo != null && networkInfo.isConnected()) {
            android.app.LoaderManager loaderManager = getLoaderManager();
            loaderManager.initLoader(NEWS_LOADER_ID, null, this);

            noFoundNews.setVisibility(View.GONE);
            Toast.makeText(this, "The connection was successful", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.GONE);
            noFoundNews.setVisibility(View.VISIBLE);
            noFoundNews.setText(R.string.connection_failing);
            Toast.makeText(this, "The connection was failed", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
        }

    }

    @Override
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals(getString(R.string.settings_limit_key)) ||
                key.equals(getString(R.string.settings_order_by_key))) {

            newsAdapter.clearNewsList();
            noFoundNews.setVisibility(View.GONE);

            View loadingIndicator = findViewById(R.id.loading_indicator);
            loadingIndicator.setVisibility(View.VISIBLE);

            getLoaderManager().restartLoader(NEWS_LOADER_ID, null, this);
        }
    }

    @NonNull
    public NewsLoader onCreateLoader(int id, @Nullable Bundle args) {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        String limit = sharedPreferences.getString(
                getString(R.string.settings_limit_key),
                getString(R.string.settings_limit_default));

        String orderBy = sharedPreferences.getString(
                getString(R.string.settings_order_by_key),
                getString(R.string.settings_order_by_default)
        );

        Uri baseUri = Uri.parse(GUARDIAN_REQUEST_URL);
        Uri.Builder uriBuilder = baseUri.buildUpon();

        uriBuilder.appendQueryParameter(getString(R.string.order_by), orderBy);
        uriBuilder.appendQueryParameter(getString(R.string.search_charachter), "sport");
        uriBuilder.appendQueryParameter(getString(R.string.limit_of_stories), limit);
        uriBuilder.appendQueryParameter(getString(R.string.start_date), "2020-10-01");
        uriBuilder.appendQueryParameter(getString(R.string.end_date), "2021-01-04");
        uriBuilder.appendQueryParameter(getString(R.string.show_tags), "contributor");
        uriBuilder.appendQueryParameter(getString(R.string.api_key), GUARDIAN_API_KEY);

        return new NewsLoader(this, uriBuilder.toString());
    }

    @Override
    public void onLoadFinished(android.content.Loader<List<News>> loader, List<News> news) {
        View loadingIndicator = findViewById(R.id.loading_indicator);
        loadingIndicator.setVisibility(View.GONE);

        if (news != null && !news.isEmpty()) {
            newsAdapter.setNewsList(news);
            noFoundNews.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        } else {
            noFoundNews.setVisibility(View.VISIBLE);
            noFoundNews.setText(R.string.there_is_no_data);
            Toast.makeText(this, "The connection was successful", Toast.LENGTH_LONG).show();
            recyclerView.setVisibility(View.GONE);
        }
    }

    @Override
    public void onLoaderReset(android.content.Loader<List<News>> loader) {
        newsAdapter.clearNewsList();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);

        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                newsAdapter.getFilter().filter(newText);
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            Intent settingsIntent = new Intent(this, SettingsActivity.class);
            startActivity(settingsIntent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}