package com.mad.meditate.ArticleHome;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import com.mad.meditate.R;

public class BigArticleHolder extends RecyclerView.ViewHolder {

    ImageView background;
    TextView title;
    CardView cardView;

    public BigArticleHolder(@NonNull View itemView) {
        super(itemView);
        this.background = itemView.findViewById(R.id.articleBigImg);
        this.title = itemView.findViewById(R.id.articleBigTxt);
        this.cardView = itemView.findViewById(R.id.bigCardView);
    }
}
