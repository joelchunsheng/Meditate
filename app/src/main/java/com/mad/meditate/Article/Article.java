package com.mad.meditate.Article;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.mad.meditate.ArticleHome.ArticleHome;
import com.mad.meditate.Meditation.MeditationActivity;
import com.mad.meditate.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class Article extends AppCompatActivity {
    FirebaseFirestore db;
    ImageView headerImg;
    TextView title, author, text, bonusTitle, bonusDes;
    ImageButton backBtn;
    CardView bonusCard;

    private static final String TAG = "Article Activity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        backBtn = findViewById(R.id.aBackBtn);
        title = findViewById(R.id.articleTitle);
        author = findViewById(R.id.articleAuthor);
        text = findViewById(R.id.articleText);
        headerImg = findViewById(R.id.articleImage);
        bonusTitle = findViewById(R.id.bonusTitleTxt);
        bonusDes = findViewById(R.id.bonusDesTxt);
        bonusCard = findViewById(R.id.bonusCard);

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
            bonusTitle.setText("Getting Started");
            bonusDes.setText("Begin your meditation journey.");
        }
        else if (intentTitle.equalsIgnoreCase("Starter guide to self improvement.")){
            headerImg.setImageResource(R.drawable.article2);
            bonusTitle.setText("Letting Go of Stress");
            bonusDes.setText("Letting negative energy go.");
        }
        else if (intentTitle.equalsIgnoreCase("Getting better sleep.")){
            headerImg.setImageResource(R.drawable.article3);
            bonusTitle.setText("Acceptance");
            bonusDes.setText("Let go of resistance and find acceptance.");
        }
        else if (intentTitle.equalsIgnoreCase("How to Meditate.")){
            headerImg.setImageResource(R.drawable.articles1);
            bonusTitle.setText("Getting Started");
            bonusDes.setText("Begin your meditation journey");
        }
        else if (intentTitle.equalsIgnoreCase("How to build a perfect wind-down routine.")){
            headerImg.setImageResource(R.drawable.articles2);
            bonusTitle.setText("Acceptance");
            bonusDes.setText("Let go of resistance and find acceptance.");
        }
        else if (intentTitle.equalsIgnoreCase("Relaxing our grip on sleep.")){
            headerImg.setImageResource(R.drawable.articles3);
            bonusTitle.setText("Acceptance");
            bonusDes.setText("Let go of resistance and find acceptance.");
        }

        bonusCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent meditationActivity = new Intent(Article.this, MeditationActivity.class);
                meditationActivity.putExtra("iTitle", bonusTitle.getText().toString());
                meditationActivity.putExtra("iDes", bonusDes.getText().toString());
                startActivity(meditationActivity);
            }
        });

        //retrieve article from firestore
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
