package com.example.administrator.pictureapp.bean;

import java.util.List;

/**
 * Created by funnyrun on 2017/5/24.
 */

public class PictureListBean {
    //下一页的 url
    private String nextPageUrl;
    private List<PictureBean> pictureBeanList;

    public PictureListBean(String nextPageUrl, List<PictureBean> pictureBeanList) {
        this.nextPageUrl = nextPageUrl;
        this.pictureBeanList = pictureBeanList;
    }

    public PictureListBean() {
    }

    public String getNextPageUrl() {
        return nextPageUrl;
    }

    public void setNextPageUrl(String nextPageUrl) {
        this.nextPageUrl = nextPageUrl;
    }


    public List<PictureBean> getPictureBeanList() {
        return pictureBeanList;
    }

    public void setPictureBeanList(List<PictureBean> pictureBeanList) {
        this.pictureBeanList = pictureBeanList;
    }

    public static class PictureBean{
        //图片套图的详情html 的 ulr
        private String htmlUrl;

        //图片套图的第一张图片的 url
        private String imageUlr;

        //图片套图的描述
        private String imageDescribe;

        //图片套图的张数
        private String imageCount;


        public PictureBean() {
        }

        public PictureBean(String htmlUrl, String imageUlr, String imageDescribe, String imageCount) {
            this.htmlUrl = htmlUrl;
            this.imageUlr = imageUlr;
            this.imageDescribe = imageDescribe;
            this.imageCount = imageCount;
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

}
