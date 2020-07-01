package com.android.meditate.ArticleHome;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.Article.Article;
import com.android.meditate.R;

import java.util.ArrayList;

public class BigArticleAdapter extends RecyclerView.Adapter<BigArticleHolder> {

    Context c;
    ArrayList<ArticleModel> bigArticleModelArrayList;

    public BigArticleAdapter(Context c, ArrayList<ArticleModel> bigArticleModelArrayList) {
        this.c = c;
        this.bigArticleModelArrayList = bigArticleModelArrayList;
    }

    @NonNull
    @Override
    public BigArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.articles_big,parent,false);
        final BigArticleHolder bigArticleHolder = new BigArticleHolder(view);

        bigArticleHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, Article.class);
                intent.putExtra("title", bigArticleModelArrayList.get(bigArticleHolder.getAdapterPosition()).getTitle());
                c.startActivity(intent);
            }
        });

        return bigArticleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull BigArticleHolder holder, int position) {
        holder.title.setText(bigArticleModelArrayList.get(position).getTitle());
        holder.background.setImageResource(bigArticleModelArrayList.get(position).getBackground());
    }

    @Override
    public int getItemCount() {
        return bigArticleModelArrayList.size();
    }
}
