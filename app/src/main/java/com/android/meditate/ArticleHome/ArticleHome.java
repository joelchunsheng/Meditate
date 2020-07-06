package com.android.meditate.ArticleHome;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.android.meditate.MainActivity;
import com.android.meditate.R;

import java.util.ArrayList;

public class ArticleHome extends AppCompatActivity {

    private RecyclerView bigArticle;
    private RecyclerView smallArticle;
    private ArrayList<ArticleModel> listBigArticles;
    private ArrayList<ArticleModel> listSmallArticles;
    ImageButton backBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article_home);

        backBtn = findViewById(R.id.maBackBtn);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent mainActivity = new Intent(ArticleHome.this, MainActivity.class);
                startActivity(mainActivity);
            }
        });

        setUpBigArticles();
        setUpSmallArticles();

        bigArticle = (RecyclerView) findViewById(R.id.bigArticlesRecycler);
        smallArticle = (RecyclerView) findViewById(R.id.smallArticlesRecycler) ;

        BigArticleAdapter bigArticleAdapter = new BigArticleAdapter(this, listBigArticles);
        bigArticle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        bigArticle.setAdapter(bigArticleAdapter);

        SmallArticleAdapter smallArticleAdapter = new SmallArticleAdapter(this, listSmallArticles);
        smallArticle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        smallArticle.setAdapter(smallArticleAdapter);

    }

    public void setUpBigArticles(){
        listBigArticles = new ArrayList<>();
        listBigArticles.add(new ArticleModel("3 Simple ways to relax.", R.drawable.articleb1));
        listBigArticles.add(new ArticleModel("Starter guide to self improvement.", R.drawable.articleb2));
        listBigArticles.add(new ArticleModel("Getting better sleep.", R.drawable.articleb3));
    }

    public void setUpSmallArticles(){
        listSmallArticles = new ArrayList<>();
        listSmallArticles.add(new ArticleModel("How to Meditate.", R.drawable.articles1));
        listSmallArticles.add(new ArticleModel("How to build a perfect wind-down routine.", R.drawable.articles2));
        listSmallArticles.add(new ArticleModel("Relaxing our grip on sleep.", R.drawable.articles3));
    }
}
