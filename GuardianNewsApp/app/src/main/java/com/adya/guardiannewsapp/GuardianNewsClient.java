package com.adya.guardiannewsapp;

import android.os.AsyncTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class GuardianNewsClient extends AsyncTask<String, Integer, String> {

    OnProgressUpdate onProgressUpdate;
    NewsAdapter newsAdapter;
    List<NewsArticle> newsArticleList = new ArrayList<>();

    GuardianNewsClient(OnProgressUpdate onProgressUpdate, NewsAdapter newsAdapter) {
        this.onProgressUpdate = onProgressUpdate;
        this.newsAdapter = newsAdapter;
    }


    public void fetchNews(String query) {
        execute("https://content.guardianapis.com/search?api-key=4f732a4a-b27e-4ac7-9350-e9d0b11dd949&q=" + query);
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        onProgressUpdate.setProgressUpdate(values[0]);
        if (values[0] == 100) {
            newsAdapter.setNews(newsArticleList);
        }
    }

    @Override
    protected String doInBackground(String... strings) {
        // start loading
        publishProgress(1);
        try {
            /// Get the Url from the parameters
            URL url = new URL(strings[0]);
            /// Make the request
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            InputStream inputStream = httpURLConnection.getInputStream();

            // json is UTF-8 by default
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8), 8);
            StringBuilder sb = new StringBuilder();
            String line = null;
            while (true) {
                try {
                    if ((line = reader.readLine()) == null) break;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                sb.append(line).append("\n");
            }
            String result = sb.toString();
            JSONObject jsonObject = new JSONObject(result);
            final JSONObject response = jsonObject.getJSONObject("response");
            final JSONArray results = response.getJSONArray("results");
            newsArticleList.clear();
            for (int i = 0; i < results.length(); i++) {
                final JSONObject articleJson = results.getJSONObject(i);

                final NewsArticle newsArticle = new NewsArticle(
                        articleJson.getString("id"),
                        articleJson.getString("sectionName"),
                        articleJson.getString("webPublicationDate"),
                        articleJson.getString("webTitle"),
                        articleJson.getString("webUrl"),
                        articleJson.getString("pillarName")
                );
                newsArticleList.add(newsArticle);
            }
        } catch (IOException | JSONException e) {
            e.printStackTrace();
        } finally {
            // finish loading
            publishProgress(100);
        }
        return null;
    }
}
