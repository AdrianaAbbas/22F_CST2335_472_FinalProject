package com.adya.guardiannewsapp;


import android.app.SearchManager;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements OnProgressUpdate {

    NewsAdapter newsAdapter;
    GuardianNewsClient guardianNewsClient;

    ProgressBar progressBar;
    ListView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String versionName = BuildConfig.VERSION_NAME;

        this.setTitle("Guardian News - " + versionName);

        View parentLayout = findViewById(R.id.constraintLayout);
        newsAdapter = new NewsAdapter(this, false, parentLayout);
        guardianNewsClient = new GuardianNewsClient(this, newsAdapter);
        progressBar = findViewById(R.id.newsPB);
        newsList = findViewById(R.id.newsList);

        newsList.setAdapter(newsAdapter);

        // Get the intent, verify the action and get the query
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            searchNews(query);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.main_menu_search) {
            Intent i = new Intent(MainActivity.this, SearchNewsActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.main_menu_favorites) {
            Intent i = new Intent(MainActivity.this, FavoritesActivity.class);
            startActivity(i);
        } else if (item.getItemId() == R.id.main_menu_info) {
            new AlertDialog.Builder(this)
                    .setTitle("Guardian News List")
                    .setMessage("Press the Search button to open the search activity. When you search a list of articles will be display here!")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void searchNews(String query) {
        guardianNewsClient.fetchNews(query);
    }


    @Override
    public void setProgressUpdate(int value) {
        if (value == 1) {
            progressBar.setVisibility(View.VISIBLE);
        }
        if (value == 100) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

}