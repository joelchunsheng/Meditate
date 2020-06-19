package com.android.meditate.Shop;

public class ShopItemModel {

    private String title, des;
    private int meditateImg, lockImg;

    public ShopItemModel(String title, String des, int meditateImg, int lockImg) {
        this.title = title;
        this.des = des;
        this.meditateImg = meditateImg;
        this.lockImg = lockImg;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public int getMeditateImg() {
        return meditateImg;
    }

    public void setMeditateImg(int meditateImg) {
        this.meditateImg = meditateImg;
    }

    public int getLockImg() {
        return lockImg;
    }

    public void setLockImg(int lockImg) {
        this.lockImg = lockImg;
    }
}
