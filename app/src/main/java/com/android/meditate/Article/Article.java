package com.android.meditate.Article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.meditate.ArticleHome.ArticleHome;
import com.android.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Article extends AppCompatActivity {
    FirebaseFirestore db;
    ImageView headerImg, backBtn;
    TextView title, author, text;

    private static final String TAG = "Article Activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        backBtn = findViewById(R.id.aBackImg);
        title = findViewById(R.id.articleTitle);
        author = findViewById(R.id.articleAuthor);
        text = findViewById(R.id.articleText);
        headerImg = findViewById(R.id.articleImage);

        backBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent articleHome = new Intent(Article.this, ArticleHome.class);
                startActivity(articleHome);
            }
        });

        Intent intent = getIntent();
        String intentTitle = intent.getStringExtra("title");
        title.setText(intentTitle);

        if (intentTitle.equalsIgnoreCase("3 Simple ways to relax.")){
            headerImg.setImageResource(R.drawable.article1);
        }
        else if (intentTitle.equalsIgnoreCase("Starter guide to self improvement.")){
            headerImg.setImageResource(R.drawable.article2);
        }
        else if (intentTitle.equalsIgnoreCase("Getting better sleep.")){
            headerImg.setImageResource(R.drawable.article3);
        }
        else if (intentTitle.equalsIgnoreCase("How to Meditate.")){
            headerImg.setImageResource(R.drawable.articles1);
        }
        else if (intentTitle.equalsIgnoreCase("How to build a perfect wind-down routine.")){
            headerImg.setImageResource(R.drawable.articles2);
        }
        else if (intentTitle.equalsIgnoreCase("Relaxing our grip on sleep.")){
            headerImg.setImageResource(R.drawable.articles3);
        }

        //retrieve article from firestore

        // Access a Cloud Firestore instance from your Activity
        db = FirebaseFirestore.getInstance();
        db.collection("articles")
                .whereEqualTo("name", intentTitle)
                .get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Log.d(TAG, document.getId() + " => " + document.getData());
                                author.setText(document.getString("author"));

                                text.setText(document.getString("text").replaceAll("/n", "\n"));
                            }
                        } else {
                            Log.w(TAG, "Error getting documents.", task.getException());
                        }
                    }
                });


    }
}
