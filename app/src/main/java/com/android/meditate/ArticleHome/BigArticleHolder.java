package com.android.meditate.ArticleHome;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.meditate.R;

public class BigArticleHolder extends RecyclerView.ViewHolder {

    ImageView background;
    TextView title;

    public BigArticleHolder(@NonNull View itemView) {
        super(itemView);
        this.background = itemView.findViewById(R.id.articleBigImg);
        this.title = itemView.findViewById(R.id.articleBigTxt);
    }
}
