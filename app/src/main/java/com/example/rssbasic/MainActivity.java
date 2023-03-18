package com.example.rssbasic;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView rssRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rssRecyclerView = findViewById(R.id.rssRecyclerView);
        rssRecyclerView.setLayoutManager(new LinearLayoutManager(this));


        List<RssItem> rssItems = new ArrayList<>();

        new Thread(() -> {
            try {
                Document doc = Jsoup.connect("https://www.nasa.gov/rss/dyn/breaking_news.rss").get();
                Elements items = doc.select("item");

                for (Element item : items) {
                    String title = item.select("title").text();
                    String description = item.select("description").text();
                    String imageUrl = item.select("enclosure").attr("url");
                    String link = item.select("link").text();

                    rssItems.add(new RssItem(title, description, imageUrl, link));
                }

                runOnUiThread(() -> {
                    RssAdapter adapter = new RssAdapter(rssItems, MainActivity.this);
                    rssRecyclerView.setAdapter(adapter);
                });

            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }
}