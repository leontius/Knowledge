package com.dante.knowledge.test;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.dante.knowledge.R;
import com.dante.knowledge.net.Net;
import com.zhy.http.okhttp.callback.StringCallback;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import okhttp3.Call;

public class TestActivity extends AppCompatActivity {

    String main = "http://keet.p2pdown.net/pw/thread.php?fid=14";
    String post = "http://keet.p2pdown.net/pw/htm_data/14/1602/298340.html";
    private int type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                start(post, 1);
            }
        });
    }

    private void start(String url, final int type) {
        Net.get(url, new StringCallback() {
            @Override
            public void onError(Call call, Exception e) {

            }

            @Override
            public void onResponse(String response) {
                if (type == 0) {
                    parsePosts(response);
                } else if (type == 1) {
                    parseImages(response);
                }
            }
        }, this);
    }

    private void parseImages(String response) {
        Document document = Jsoup.parse(response);
        Elements elements = document.select(".tpc_content > img[src]");
        for (int i = 0; i < elements.size(); i++) {
            Element img = elements.get(i);
            String src = img.attr("src");
            Log.i("test", " src>>>" + src);
        }
    }

    private void parsePosts(String response) {
        Document document = Jsoup.parse(response);
        Elements elements = document.select(".tr3 > td > h3 > a");
        for (int i = 3; i < elements.size(); i++) {
            Element post = elements.get(i);
            String link = post.attr("abs:href");
            String title = post.text();
            Log.i("test", "href>>>" + link + " text>>>" + title);
        }
    }

}
