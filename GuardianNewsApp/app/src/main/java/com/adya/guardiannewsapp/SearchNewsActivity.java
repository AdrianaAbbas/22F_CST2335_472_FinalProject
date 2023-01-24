package com.adya.guardiannewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.adya.guardiannewsapp.R;

public class SearchNewsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_news);
        this.setTitle("Search Articles");

        onSearchRequested();

        Button button = findViewById(R.id.search_button);
        button.setOnClickListener(view -> {
            onSearchRequested();
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.search_menu_info) {
            new AlertDialog.Builder(this)
                    .setTitle("Search Engine")
                    .setMessage("You closed the prompt box? No worries press the button in the center!")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}