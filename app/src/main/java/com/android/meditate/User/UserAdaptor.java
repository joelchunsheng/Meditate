package com.android.meditate.User;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.meditate.Login.LoginActivity;
import com.android.meditate.MainActivity;
import com.android.meditate.Meditation.MeditationActivity;
import com.android.meditate.Notification.Notification;
import com.android.meditate.R;

import java.util.ArrayList;

public class UserAdaptor extends RecyclerView.Adapter<UserViewHolder> {

    private static final String TAG = "UserAdaptor";
    private ArrayList<String> data;

    public UserAdaptor(ArrayList<String> settingsList){
        data = settingsList;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_settings_row, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {

        holder.settingText.setText(data.get(position));
        String text = holder.settingText.getText().toString();
        if (text.equals("About")){
            holder.settingText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // GO TO ABOUT ACTIVITY
                }
            });
        }
        else if (text.equals(("Notifications"))){
            holder.settingText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), Notification.class);
                    view.getContext().startActivity(intent);
                }
            });
        }
        else if (text.equals("Log Out")){
            holder.settingText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(view.getContext(), LoginActivity.class);
                    view.getContext().startActivity(intent);

                }
            });
        }
        else{
            holder.settingText.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.v(TAG, "Button not assigned");
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return data.size();
    }
}
