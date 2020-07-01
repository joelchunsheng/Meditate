package com.android.meditate.ArticleHome;

import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.android.meditate.R;


public class SmallArticleHolder  extends RecyclerView.ViewHolder{

    ImageView background;
    TextView title;
    LinearLayout linearLayout;

    public SmallArticleHolder(@NonNull View itemView) {
        super(itemView);
        this.background = itemView.findViewById(R.id.articleSmallImg);
        this.title = itemView.findViewById(R.id.articleSmallTxt);
        this.linearLayout = itemView.findViewById(R.id.smallLinearLayout);
    }
}
