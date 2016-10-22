package com.example.teacher.atndapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class EventDetailActivity extends AppCompatActivity {

    // webviewでロードするためのhtmlデータ引き渡し用
    public final static String EXTRA_WEB_VIEW_HTML_LOAD_DATA =
            "EXTRA_WEB_VIEW_HTML_LOAD_DATA";

    @BindView(R.id.web_main)
    WebView webViewMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);
        ButterKnife.bind(this);

        // 前の画面から読み込むhtmlデータを取得する
        Intent intent = getIntent();
        String loadHTML =
                intent.getStringExtra(EXTRA_WEB_VIEW_HTML_LOAD_DATA);

        if(!TextUtils.isEmpty(loadHTML)) {
            webViewMain.setWebViewClient(new WebViewClient());
            webViewMain.getSettings().setJavaScriptEnabled(true);

            loadHTML = "<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"></head><body>" +
                    loadHTML +
                    "</body></html>";

            webViewMain.loadDataWithBaseURL(null,loadHTML, "text/html", "utf-8",null);
        }
    }
}
