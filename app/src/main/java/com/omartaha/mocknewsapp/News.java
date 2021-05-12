package com.omartaha.mocknewsapp;

import java.util.ArrayList;

public class News {

    private String newsTitle;
    private String newsDateTime;
    private String newsSection;
    private String newsType;
    private String newsUrl;
    private ArrayList<String> authors;


    public News(String newsDateTime, String newsSection, String newsTitle, String newsType, String newsUrl, ArrayList<String> authors) {
        this.newsDateTime = newsDateTime;
        this.newsSection = newsSection;
        this.newsTitle = newsTitle;
        this.newsType = newsType;
        this.newsUrl = newsUrl;
        this.authors = authors;
    }

    public String getNewsUrl() {
        return newsUrl;
    }

    public String getNewsDateTime() {
        return newsDateTime;
    }

    public String getNewsSection() {
        return newsSection;
    }

    public String getNewsTitle() {
        return newsTitle;
    }

    public String getNewsType() {
        return newsType;
    }

    public ArrayList<String> getAuthors() {
        return authors;
    }
}
