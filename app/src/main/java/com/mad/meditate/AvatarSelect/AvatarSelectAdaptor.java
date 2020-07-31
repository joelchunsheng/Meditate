package com.mad.meditate.AvatarSelect;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mad.meditate.R;
import com.mad.meditate.User.UserFragment;

import java.util.ArrayList;

public class AvatarSelectAdaptor extends RecyclerView.Adapter<AvatarSelectViewHolder> {

    private static final String TAG = "AvatarSelectAdaptor";
    private ArrayList<String> profilePictures;
    private SharedPreferences userPref;
    private int selectedPos = RecyclerView.NO_POSITION;

    public AvatarSelectAdaptor(ArrayList<String> profilePictureList){
        this.profilePictures = profilePictureList;
    }


    @NonNull
    @Override
    public AvatarSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_avatar_select_viewholder, parent, false);
        userPref = parent.getContext().getSharedPreferences("com.android.meditate.User", Context.MODE_PRIVATE);

        selectedPos = profilePictures.indexOf(userPref.getString("selectedAvatar", "avatar_trees"));

        return new AvatarSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final AvatarSelectViewHolder holder, final int position) {
        if (selectedPos == position){
            holder.avatarSelectItemView.setBackgroundColor(Color.parseColor("#C9E4DE"));
        }
        else{
            holder.avatarSelectItemView.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        final int resId = holder.itemView.getResources().getIdentifier(profilePictures.get(position), "drawable", holder.itemView.getContext().getPackageName());
        holder.avatarSelectItemImageView.setImageResource(resId);
        holder.avatarSelectItemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserFragment.userAvatar.setImageResource(resId);
                userPref.edit().putString("selectedAvatar", profilePictures.get(position)).apply();
                Log.v(TAG, "User changed avatar to " + profilePictures.get(position));
                selectedPos = position;
                notifyDataSetChanged();
            }
        });
    }

    @Override
    public int getItemCount() {
        return profilePictures.size();
    }
}
