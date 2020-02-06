package com.example.android.lapitchat;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class allUserAdapter extends RecyclerView.Adapter<allUserAdapter.ViewHolder> {
    ArrayList< UserInfo> reallist;
    public allUserAdapter(ArrayList<UserInfo> lit) {
        //Log.d("raj", "allUserAdapter: ");
        reallist=lit;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info,parent,false);
        //Log.d("raj", "onCreateViewHolder: ");
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.name.setText(reallist.get(position).getName());
        holder.status.setText(reallist.get(position).getStatus());
        Picasso.get().load(reallist.get(position).getImage()).into(holder.dp);
        //Log.d("raj", "onBindViewHolder: "+position+" ");


    }

    @Override
    public int getItemCount() {
        return reallist.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView name,status;
        ImageView dp;

        public ViewHolder(View view) {
            super(view);
            name=view.findViewById(R.id.IdUserName);
            status=view.findViewById(R.id.IdUserstatus);
            dp=view.findViewById(R.id.circleImageView);
        }
    }
}
