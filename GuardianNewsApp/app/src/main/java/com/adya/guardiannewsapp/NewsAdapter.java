package com.adya.guardiannewsapp;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

public class NewsAdapter extends BaseAdapter {

    List<NewsArticle> news = new ArrayList<>();

    private final Context context;
    private final MyOpener dbOpener;
    private final boolean isFavList;
    private final View snackBarParentView;

    public NewsAdapter(Context context, boolean isFavList, View snackBarParentView) {
        this.context = context;
        this.isFavList = isFavList;
        this.snackBarParentView = snackBarParentView;
        dbOpener = new MyOpener(context);
    }

    public void setNews(List<NewsArticle> news) {
        this.news = news;
        notifyDataSetChanged();
    }

    public List<NewsArticle> getFavoriteNews() {
        SQLiteDatabase db = dbOpener.getReadableDatabase();
        String[] columns = {
                MyOpener.COL_ID,
                MyOpener.COL_TITLE,
                MyOpener.COL_PILLAR,
                MyOpener.COL_SECTION,
                MyOpener.COL_PUBLISHED_DATE,
                MyOpener.COL_URL,
        };
        Cursor c = db.query(false, MyOpener.TABLE_NAME, columns, null, null, null, null, null, null);

        List<NewsArticle> newsArticleList = new ArrayList<>();

        int idColumnIndex = c.getColumnIndex(MyOpener.COL_ID);
        int titleColumnIndex = c.getColumnIndex(MyOpener.COL_TITLE);
        int pillarColumnIndex = c.getColumnIndex(MyOpener.COL_PILLAR);
        int sectionColumnIndex = c.getColumnIndex(MyOpener.COL_SECTION);
        int publishedDateColumnIndex = c.getColumnIndex(MyOpener.COL_PUBLISHED_DATE);
        int urlColumnIndex = c.getColumnIndex(MyOpener.COL_URL);

        while (c.moveToNext()) {
            NewsArticle article = new NewsArticle(
                    c.getString(idColumnIndex),
                    c.getString(sectionColumnIndex),
                    c.getString(publishedDateColumnIndex),
                    c.getString(titleColumnIndex),
                    c.getString(urlColumnIndex),
                    c.getString(pillarColumnIndex)
            );
            newsArticleList.add(article);
        }
        c.close();
        return newsArticleList;
    }

    public void addArticle(NewsArticle article) {
        if(isFavList) {
            news.add(article);
        }
        SQLiteDatabase dBase = dbOpener.getWritableDatabase();
        ContentValues cv = new ContentValues(); // a new row in the database
        cv.put(MyOpener.COL_ID, article.id);
        cv.put(MyOpener.COL_PILLAR, article.pillarName);
        cv.put(MyOpener.COL_SECTION, article.sectionName);
        cv.put(MyOpener.COL_URL, article.webUrl);
        cv.put(MyOpener.COL_TITLE, article.webTitle);
        cv.put(MyOpener.COL_PUBLISHED_DATE, article.webPublicationDate);
        dBase.insertOrThrow(MyOpener.TABLE_NAME, "NullColumnName", cv);
        notifyDataSetChanged();

        CharSequence text = "Article added to favourites!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }

    public void removeArticle(int index, String id) {
        NewsArticle article = news.get(index);
        if(isFavList) {
            news.remove(index);
        }
        SQLiteDatabase dBase = dbOpener.getWritableDatabase();
        dBase.delete(MyOpener.TABLE_NAME, MyOpener.COL_ID + "= ?", new String[]{id});
        notifyDataSetChanged();


        Snackbar snackbar = Snackbar
                .make(snackBarParentView, "Article removed from favourites is deleted", Snackbar.LENGTH_LONG)
                .setAction("UNDO", view -> addArticle(article));
        snackbar.show();
    }

    @Override
    public int getCount() {
        return news.size();
    }

    @Override
    public Object getItem(int i) {
        return news.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View newView = view;
        LayoutInflater inflater = LayoutInflater.from(context);

        if (newView == null) {
            newView = inflater.inflate(R.layout.news_article, viewGroup, false);
        }

        final NewsArticle newsArticle = news.get(i);

        newView.setOnClickListener(view1 -> {
            Intent intent = new Intent(context, DetailsActivity.class);
            intent.putExtra("article", newsArticle);
            context.startActivity(intent);
        });
        final TextView articleTitle = newView.findViewById(R.id.articleTitle);
        articleTitle.setText(newsArticle.webTitle);

        final TextView articleSubtitle = newView.findViewById(R.id.articleSubtitle);
        articleSubtitle.setText(newsArticle.sectionName);

        final ImageView favoriteIcon = newView.findViewById(R.id.favoriteButton);
        List<NewsArticle> articles = getFavoriteNews();
        for (NewsArticle article : articles) {
            if (Objects.equals(article.id, newsArticle.id)) {
                favoriteIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_baseline_star_24));
            }
        }

        favoriteIcon.setOnClickListener(view12 -> {
            List<NewsArticle> arts = getFavoriteNews();
            boolean removed = false;
            for (NewsArticle article : arts) {
                if (Objects.equals(article.id, newsArticle.id)) {
                    removeArticle(i, newsArticle.id);
                    removed = true;
                    favoriteIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_baseline_star_outline_24));
                }
            }
            if (!removed) {
                addArticle(newsArticle);
                favoriteIcon.setImageDrawable(AppCompatResources.getDrawable(context, R.drawable.ic_baseline_star_24));
            }
        });

        TemporalAccessor ta = DateTimeFormatter.ISO_INSTANT.parse(newsArticle.webPublicationDate);
        Instant instant = Instant.from(ta);
        Date articleDate = Date.from(instant);

        final TextView articleFooter = newView.findViewById(R.id.articleFooter);
        articleFooter.setText(new SimpleDateFormat("yyyy-MM-dd").format(articleDate));

        return newView;
    }
}
