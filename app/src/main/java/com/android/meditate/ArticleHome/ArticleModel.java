package com.android.meditate.ArticleHome;

public class ArticleModel {

    private String title;
    private int background;

    public ArticleModel(String title, int background) {
        this.title = title;
        this.background = background;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getBackground() {
        return background;
    }

    public void setBackground(int background) {
        this.background = background;
    }
}
