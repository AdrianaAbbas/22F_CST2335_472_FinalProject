package com.adya.guardiannewsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

public class DetailsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        this.setTitle(getString(R.string.dets_title));

        Intent intent = getIntent();
        NewsArticle article = (NewsArticle) intent.getSerializableExtra("article");

        final TextView articleTitle = findViewById(R.id.articleTitle);
        articleTitle.setText(article.webTitle);


        final TextView articleSection = findViewById(R.id.articleSubtitle);
        articleSection.setText(article.sectionName);

        final TextView articleUrl = findViewById(R.id.articleWebUrl);
        articleUrl.setText(article.webUrl);
        articleUrl.setOnClickListener(view -> {
            String url = article.webUrl;
            Intent urlIntent = new Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse(url)
            );
            startActivity(urlIntent);
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.details_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.details_menu_info) {
            new AlertDialog.Builder(this)
                    .setTitle(R.string.about_dets_title)
                    .setMessage(R.string.about_dets_content)
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return true;
        } else if(item.getItemId() == android.R.id.home) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

}