package com.bluewhaleyt.whaleutils.debug.models;

public class ColorResModel {

    int count;
    String colorHex, colorRes;

    public ColorResModel() {

    }

    public ColorResModel(int count, String colorHex, String colorRes) {
        this.count = count;
        this.colorHex = colorHex;
        this.colorRes = colorRes;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setColorHex(String colorHex) {
        this.colorHex = colorHex;
    }

    public String getColorHex() {
        return colorHex;
    }

    public void setColorRes(String colorRes) {
        this.colorRes = colorRes;
    }

    public String getColorRes() {
        return colorRes;
    }

}
