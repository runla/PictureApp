package com.example.administrator.pictureapp.utils;

import android.util.Log;

import com.example.administrator.pictureapp.bean.PictureFirstBean;
import com.example.administrator.pictureapp.bean.PictureListBean;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

import static com.example.administrator.pictureapp.common.ApiHelper.BASE_URL;

/**
 * Created by funnyrun on 2017/5/24.
 */

public class JsoupUtil {

    public static void Jsoup(final String url, final int category, final String categoryName, final PageCallback pageCallback){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(url)
                            .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                            .get();
                    handlePictureList(category,categoryName,document,pageCallback);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    /**
     * 这个方法是用来处理某种类型图片的列表数据（如：写真、校花这些）
     * @param document
     * @return
     */
    public static PictureListBean handlePictureList(int category,String categoryName,Document document,PageCallback pageCallback){
        PictureFirstBean pictureFirstBean = new PictureFirstBean();

        PictureListBean pictures = new PictureListBean();
        List<PictureListBean.PictureBean> pictureBeanList = new ArrayList<>();


        //这一步中的选择器获取了每个类型中的一个页面的图片
        Element content = document.select("div.lb_box").first();
        Elements elements = content.select("dl");
        for(Element link :elements){
            String imageUrl = link.select("dt").select("img").attr("src");
            String htmlUrl = link.select("dd").select("a").attr("href");
            String imageCount = link.select("dd").select("span").text();
            String imageDescribe = link.select("dt").select("img").attr("alt");


            pictureFirstBean.setCategory(category);
            pictureFirstBean.setCategoryName(categoryName);
            pictureFirstBean.setHtmlUrl(htmlUrl);
            pictureFirstBean.setImageCount(imageCount);
            pictureFirstBean.setImageDescribe(imageDescribe);
            pictureFirstBean.setImageUlr(imageUrl);
            pictureFirstBean.save(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if(e==null){
                        Log.d("bmob","done: "+"创建数据成功：" + s);
                    }else{
                        Log.i("bmob","失败："+e.getMessage()+","+e.getErrorCode());
                    }
                }

            });

            PictureListBean.PictureBean pictureBean = new PictureListBean.PictureBean();
            //图片 url
            pictureBean.setImageUlr(link.select("dt").select("img").attr("src"));
            //图片描述(标题)
            pictureBean.setImageDescribe(link.select("dt").select("img").attr("alt"));
            //图片详情 url
            pictureBean.setHtmlUrl(link.select("dd").select("a").attr("href"));
            //图片数量
            pictureBean.setImageCount(link.select("dd").select("span").text());

            pictureBeanList.add(pictureBean);
        }
        pictures.setPictureBeanList(pictureBeanList);

        //这个是页数的选择的处理 （上一页、1、2、3、4、下一页）
        Elements pages = document.select("div.flym").first().getElementsByTag("a");
        String url = "";
        for(Element page:pages){
            //找到有下一页字样的说明还可以加载更多
            String text = page.text();
            if ("下一页".equals(text)) {
                pictures.setNextPageUrl(page.attr("href"));
                url = BASE_URL+pictures.getNextPageUrl();
            }
        }
        pageCallback.pageUrl(url,category,categoryName);
        return pictures;
    }

    public interface PageCallback{
        void pageUrl(String url,int category,String categoryName);
    }
}
