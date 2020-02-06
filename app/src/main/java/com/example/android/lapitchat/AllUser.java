package com.example.android.lapitchat;

import android.os.Bundle;
import android.transition.Explode;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

public class AllUser extends AppCompatActivity {
    Toolbar mtoolbar;
    RecyclerView recyclerView;
    DatabaseReference ref;
    FirebaseRecyclerOptions<UserInfo> option;
    ArrayList<UserInfo> all;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getwindowanimation();
        setContentView(R.layout.activity_all_user);
        //getwindowanimation();

        mtoolbar=findViewById(R.id.allUser_action_bar);
        setSupportActionBar(mtoolbar);

        getSupportActionBar().setTitle("All Users");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ref=FirebaseDatabase.getInstance().getReference().child("Users");

        all = new ArrayList<>();
        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {

                    for (DataSnapshot snap : dataSnapshot.getChildren()) {
                        all.add(snap.getValue(UserInfo.class));
                        //Log.d("raj",snap.getValue(UserInfo.class).getName()+" ");

                    }
                    recyclerView.setAdapter(new allUserAdapter(all));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });



    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    public void getwindowanimation()
    {
        Slide slide=new Slide(Gravity.RIGHT);
        slide.setDuration(500);
        Log.d("raj", "getwindowanimation: ");
        getWindow().setEnterTransition(slide);
        Slide slide2=new Slide(Gravity.LEFT);
        slide2.setDuration(500);
        getWindow().setExitTransition(slide2);

    }




}
