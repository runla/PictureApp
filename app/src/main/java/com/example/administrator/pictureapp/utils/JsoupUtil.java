package com.example.administrator.pictureapp.utils;

import com.example.administrator.pictureapp.bean.PictureListBean;
import com.example.administrator.pictureapp.common.ApiHelper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by funnyrun on 2017/5/24.
 */

public class JsoupUtil {

    public static void Jsoup(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Document document = Jsoup.connect(ApiHelper.LEGS_URL)
                            .userAgent("Mozilla/5.0 (Windows NT 6.2; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1667.0 Safari/537.36")
                            .get();
                    handlePictureList(document);
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
    public static PictureListBean handlePictureList(Document document){
        PictureListBean pictures = new PictureListBean();
        List<PictureListBean.PictureBean> pictureBeanList = new ArrayList<>();


        //这一步中的选择器获取了每个类型中的一个页面的图片
        Element content = document.select("div.lb_box").first();
        Elements elements = content.select("dl");
        for(Element link :elements){
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
        for(Element page:pages){
            //找到有下一页字样的说明还可以加载更多
            String text = page.text();
            if ("下一页".equals(text)) {
                pictures.setNextPageUrl(page.attr("href"));
            }
        }

        return pictures;
    }
}
