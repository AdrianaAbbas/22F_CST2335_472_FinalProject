package com.adya.guardiannewsapp;


import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.drawerlayout.widget.DrawerLayout;


public class MainActivity extends AppCompatActivity implements OnProgressUpdate {

    NewsAdapter newsAdapter;
    GuardianNewsClient guardianNewsClient;
    ProgressBar progressBar;
    ListView newsList;

    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private String[] navigationDrawerItemTitles;
    ActionBarDrawerToggle drawerToggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String versionName = BuildConfig.VERSION_NAME;

        this.setTitle("Guardian News - " + versionName);

        setupToolbar();

        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        getSupportActionBar().setHomeButtonEnabled(true);

        navigationDrawerItemTitles = getResources().getStringArray(R.array.navigation_drawer_items_array);
        drawerLayout = findViewById(R.id.drawerLayout);
        drawerList = findViewById(R.id.left_drawer);

        DrawerItemAdapter adapter = new DrawerItemAdapter(this, R.layout.drawer_item_row, navigationDrawerItemTitles);
        drawerList.setAdapter(adapter);
        drawerList.setOnItemClickListener(new DrawerItemClickListener());

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

        setupDrawerToggle();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    private void selectItem(int position) {
        Intent i;
        switch (position) {
            case 0:
                i = new Intent(MainActivity.this, SearchNewsActivity.class);
                startActivity(i);
                break;
            case 1:
                i = new Intent(MainActivity.this, FavoritesActivity.class);
                startActivity(i);
            default:
                break;
        }


        drawerList.setItemChecked(position, true);
        drawerList.setSelection(position);
        setTitle(navigationDrawerItemTitles[position]);
        drawerLayout.closeDrawer(drawerList);
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
                    .setTitle(R.string.about_main_title)
                    .setMessage(R.string.about_main_content)
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

    void setupToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    void setupDrawerToggle(){
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout,toolbar,R.string.app_name, R.string.app_name);
        //This is necessary to change the icon of the Drawer Toggle upon state change.
        drawerToggle.syncState();
    }

}