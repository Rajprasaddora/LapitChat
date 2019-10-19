package com.example.android.lapitchat;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUser extends AppCompatActivity {
    Toolbar mtoolbar;
    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseRecyclerOptions<UserInfo> option;
    FirebaseRecyclerAdapter<UserInfo,mViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_user);


        mtoolbar=findViewById(R.id.allUser_action_bar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref=FirebaseDatabase.getInstance().getReference().child("Users");

        recyclerView=findViewById(R.id.recycler);
        doit();
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.setAdapter(adapter);


    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();

    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.startListening();
    }

    public class mViewHolder extends RecyclerView.ViewHolder{
        public TextView name;
        public TextView status;
        public CircleImageView image;
        public mViewHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.IdUserName);
            status=itemView.findViewById(R.id.IdUserstatus);
            image=itemView.findViewById(R.id.circleImageView);
        }
    }
    public void doit()
    {
        option=new FirebaseRecyclerOptions.Builder<UserInfo>().setQuery(ref,UserInfo.class).build();
        adapter=new FirebaseRecyclerAdapter<UserInfo, mViewHolder>(option) {
            @Override
            protected void onBindViewHolder(@NonNull mViewHolder holder, int position, @NonNull UserInfo model) {
                Picasso.get().load(model.getImage()).into(holder.image);
                holder.name.setText(model.getName());
                holder.status.setText(model.getStatus());
            }

            @NonNull
            @Override
            public mViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View mview= LayoutInflater.from(parent.getContext()).inflate(R.layout.user_info,parent,false);
                return new mViewHolder(mview);
            }
        };
    }
}
