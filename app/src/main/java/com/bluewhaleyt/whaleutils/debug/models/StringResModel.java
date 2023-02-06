package com.bluewhaleyt.whaleutils.debug.models;

public class StringResModel {

    int count;
    String stringPreview, stringRes;

    public StringResModel() {

    }

    public StringResModel(int count, String stringPreview, String stringRes) {
        this.count = count;
        this.stringPreview = stringPreview;
        this.stringRes = stringRes;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCount() {
        return count;
    }

    public void setStringPreview(String stringPreview) {
        this.stringPreview = stringPreview;
    }

    public String getStringPreview() {
        return stringPreview;
    }

    public void setStringRes(String stringRes) {
        this.stringRes = stringRes;
    }

    public String getStringRes() {
        return stringRes;
    }

}
