package com.example.administrator.pictureapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.administrator.pictureapp.utils.JsoupUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.example.administrator.pictureapp.common.ApiHelper.BEAUTIFUL_CHARM_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.BEAUTIFUL_GIRL_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.CAR_MODEL_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.JAPAN_AND_KOREAN_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.LEGS_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.LOVELY_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.MODEL_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.NON_MAINSTREAM_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.PHOTO_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.PORTRAIT_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.PURE_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.SCHOOL_FLOWER;
import static com.example.administrator.pictureapp.common.ApiHelper.SEXY_URL;
import static com.example.administrator.pictureapp.common.ApiHelper.STREET_SHOOT;
import static com.example.administrator.pictureapp.common.ApiHelper.TEMPERAMENT_URL;

public class MainActivity extends AppCompatActivity implements JsoupUtil.PageCallback{

    private String[] categoryUrl = new String[15];
    private String[] categoryName = new String[15];

    private Button btn_next;
    private Button btn_next1;
    private int count = 9;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();
        btn_next = (Button) findViewById(R.id.btn_next);
        btn_next1 = (Button) findViewById(R.id.btn_next1);
        JsoupUtil.Jsoup(categoryUrl[count],count,categoryName[count],MainActivity.this);
        btn_next.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                count++;
                JsoupUtil.Jsoup(categoryUrl[count],count,categoryName[count],MainActivity.this);
            }
        });

        btn_next1.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                btn_next1.setClickable(false);
                btn_next1.setBackgroundResource(R.color.gray);
                JsoupUtil.Jsoup(nextPageUrl,mycategory,mycategoryName,MainActivity.this);
            }
        });
//        for (int i = 0; i < 16; i++) {
//            JsoupUtil.Jsoup(categoryUrl[i],i,categoryName[i],this);
//        }
//        getData();
    }
    boolean flag = false;
    private String nextPageUrl;
    private String mycategoryName;
    private int mycategory;
    @Override
    public void pageUrl(String url, int category, String categoryName) {
        if (!"".equals(url)) {
            flag = true;
            nextPageUrl = url;
            mycategory = category;
            mycategoryName = categoryName;
            MainActivity pageCallback = this;
            JsoupUtil.Jsoup(url,category,categoryName, pageCallback);
            runOnUiThread(new Runnable() {
                @Override
                public void run() {

                    btn_next1.setClickable(true);
                    btn_next1.setBackgroundResource(R.color.colorAccent);
                }
            });
            return;
        }
//        ShowToast.Long("这一分类结束了");
        Log.d("bmob1", "pageUrl: "+"这一分类结束了");
//        Toast.makeText(this, "这一分类结束了", Toast.LENGTH_SHORT).show();
    }

    private void initData(){
        categoryUrl[0] = PORTRAIT_URL;
        categoryName[0] = "写真";
        categoryUrl[1] = TEMPERAMENT_URL;
        categoryName[1] = "气质";
        categoryUrl[2] = LOVELY_URL;
        categoryName[2] = "萌女";
        categoryUrl[3] = SCHOOL_FLOWER;
        categoryName[3] = "校花";
        categoryUrl[4] = STREET_SHOOT;
        categoryName[4] = "街拍";
        categoryUrl[5] = NON_MAINSTREAM_URL;
        categoryName[5] = "非主流";
        categoryUrl[6] = LEGS_URL;
        categoryName[6] = "美腿";
        categoryUrl[7] = PURE_URL;
        categoryName[7] = "清纯";
        categoryUrl[8] = SEXY_URL;
        categoryName[8] = "性感";
        categoryUrl[9] = JAPAN_AND_KOREAN_URL;
        categoryName[9] = "日韩";
        categoryUrl[10] = CAR_MODEL_URL;
        categoryName[10] = "车模";
        categoryUrl[11] = MODEL_URL;
        categoryName[11] = "模特";
        categoryUrl[12] = BEAUTIFUL_GIRL_URL;
        categoryName[12] = "女神图片";
        categoryUrl[13] = BEAUTIFUL_CHARM_URL;
        categoryName[13] = "美女魅惑";
        categoryUrl[14] = PHOTO_URL;
        categoryName[14] = "写真摄影";
    }

    private void getData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(LEGS_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().byteStream(),null,null);
             //   JsoupUtil.handlePictureList(document,MainActivity.this);
            }

        });
    }



}
