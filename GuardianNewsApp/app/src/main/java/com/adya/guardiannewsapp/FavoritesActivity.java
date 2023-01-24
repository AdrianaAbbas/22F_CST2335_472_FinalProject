package com.adya.guardiannewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FavoritesActivity extends AppCompatActivity {

    NewsAdapter newsAdapter;
    ListView newsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorites);
        this.setTitle("Your Favorites");

        View parentLayout = findViewById(R.id.constraintLayout);
        newsAdapter = new NewsAdapter(this, true, parentLayout);
        newsList = findViewById(R.id.newsList);
        newsList.setAdapter(newsAdapter);
        newsAdapter.setNews(newsAdapter.getFavoriteNews());
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.favorites_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.favorites_menu_info) {
            new AlertDialog.Builder(this)
                    .setTitle("Your Favorites")
                    .setMessage("This is the list of your favorite articles. Press the start to remove them.")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}