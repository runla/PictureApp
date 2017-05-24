package com.example.administrator.pictureapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.administrator.pictureapp.common.ApiHelper;
import com.example.administrator.pictureapp.utils.JsoupUtil;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        JsoupUtil.Jsoup();
//        getData();
    }

    private void getData(){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(ApiHelper.LEGS_URL)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Document document = Jsoup.parse(response.body().byteStream(),null,null);
                JsoupUtil.handlePictureList(document);
            }

        });
    }
}
