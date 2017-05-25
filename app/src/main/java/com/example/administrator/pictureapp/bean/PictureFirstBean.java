package com.example.administrator.pictureapp.bean;

import cn.bmob.v3.BmobObject;

/**
 * Created by funnyrun on 2017/5/25.
 * 描述：通过图片分类的 url 所获取到的 bean
 */

public class PictureFirstBean extends BmobObject{
    //图片类别（清纯、校花。。。）
    private int category;

    //图片套图的详情html 的 ulr
    private String htmlUrl;

    //图片套图的第一张图片的 url
    private String imageUlr;

    //图片套图的描述
    private String imageDescribe;

    //图片套图的张数
    private String imageCount;

    public PictureFirstBean() {
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getHtmlUrl() {
        return htmlUrl;
    }

    public void setHtmlUrl(String htmlUrl) {
        this.htmlUrl = htmlUrl;
    }

    public String getImageUlr() {
        return imageUlr;
    }

    public void setImageUlr(String imageUlr) {
        this.imageUlr = imageUlr;
    }

    public String getImageDescribe() {
        return imageDescribe;
    }

    public void setImageDescribe(String imageDescribe) {
        this.imageDescribe = imageDescribe;
    }

    public String getImageCount() {
        return imageCount;
    }

    public void setImageCount(String imageCount) {
        this.imageCount = imageCount;
    }
}
