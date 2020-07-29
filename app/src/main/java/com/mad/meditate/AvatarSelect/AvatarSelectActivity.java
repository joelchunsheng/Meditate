package com.mad.meditate.AvatarSelect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.mad.meditate.R;

import java.util.ArrayList;

public class AvatarSelectActivity extends AppCompatActivity {

    private static final String TAG = "AvatarSelectActivity";
    private ImageButton backButton;
    private ArrayList<String> profilePictures = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_avatar_select);

        RecyclerView recyclerView = findViewById(R.id.avatarSelectList);

        profilePictures.add("avatar_watermelon");
        profilePictures.add("avatar_octopus");
        profilePictures.add("avatar_drink");
        profilePictures.add("avatar_leaf");
        profilePictures.add("avatar_kite");
        profilePictures.add("avatar_owl");
        profilePictures.add("avatar_trees");
        AvatarSelectAdaptor adaptor = new AvatarSelectAdaptor(profilePictures);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);

        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adaptor);

        backButton = (ImageButton) findViewById(R.id.avatarSelectBackButton);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.v(TAG, "Finishing Avatar Select");
                finish();
            }
        });
    }
}
