package com.adya.guardiannewsapp;


import java.io.Serializable;

public class NewsArticle implements Serializable {
    public final String id;
    public final String sectionName;
    public final String webPublicationDate;
    public final String webTitle;
    public final String webUrl;
    public final String pillarName;

    public NewsArticle(String id, String sectionName, String webPublicationDate, String webTitle, String webUrl, String pillarName) {
        this.id = id;
        this.sectionName = sectionName;
        this.webPublicationDate = webPublicationDate;
        this.webTitle = webTitle;
        this.webUrl = webUrl;
        this.pillarName = pillarName;
    }

}
