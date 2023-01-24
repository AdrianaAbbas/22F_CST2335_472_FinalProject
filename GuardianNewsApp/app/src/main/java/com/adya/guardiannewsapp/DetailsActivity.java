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
        this.setTitle("Article Details");

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
                    .setTitle("Article Details")
                    .setMessage("Press the Url to open up the article in full")
                    .setPositiveButton(android.R.string.ok, null)
                    .setIcon(android.R.drawable.ic_dialog_info)
                    .show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}