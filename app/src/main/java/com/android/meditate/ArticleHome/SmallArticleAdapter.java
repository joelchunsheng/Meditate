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

public class SmallArticleAdapter extends RecyclerView.Adapter<SmallArticleHolder>{

    Context c;
    ArrayList<ArticleModel> smallArticleModelArrayList;

    public SmallArticleAdapter(Context c, ArrayList<ArticleModel> smallArticleModelArrayList) {
        this.c = c;
        this.smallArticleModelArrayList = smallArticleModelArrayList;
    }

    @NonNull
    @Override
    public SmallArticleHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(c).inflate(R.layout.articles_small,parent,false);
        final SmallArticleHolder smallArticleHolder = new SmallArticleHolder(view);

        smallArticleHolder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(c, Article.class);
                intent.putExtra("title", smallArticleModelArrayList.get(smallArticleHolder.getAdapterPosition()).getTitle());
                c.startActivity(intent);
            }
        });

        return smallArticleHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull SmallArticleHolder holder, int position) {
        holder.title.setText(smallArticleModelArrayList.get(position).getTitle());
        holder.background.setImageResource(smallArticleModelArrayList.get(position).getBackground());
    }

    @Override
    public int getItemCount() {
        return smallArticleModelArrayList.size();
    }
}
