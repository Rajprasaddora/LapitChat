package com.example.android.lapitchat;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
public class MainActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    TabLayout mtablayout;
    private ViewPager mViewPager;
    private SelectionPageAdapter mSelectionPageAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth=FirebaseAuth.getInstance();
        toolbar=findViewById(R.id.action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Chat app");
        mtablayout=findViewById(R.id.idTabLayout);
        mViewPager=findViewById(R.id.idTabPager);
        mSelectionPageAdapter=new SelectionPageAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mSelectionPageAdapter);
        mtablayout.setupWithViewPager(mViewPager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.main_menu,menu);
        return true;
    }

    public void onStart()
    {
        super.onStart();
        FirebaseUser currentUser=mAuth.getCurrentUser();
        if(currentUser==null)
        {
            startmain();
        }
    }
    public void startmain()
    {
        Intent startIntent=new Intent(this,StartActivity.class);
        startActivity(startIntent);
        finish();
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        if(item.getItemId()==R.id.idLogOut)
        {
            FirebaseAuth.getInstance().signOut();
            startmain();
        }
        else if(item.getItemId()==R.id.idAccountSetting)
        {
            Intent intent=new Intent(this,settingActivity.class);
            startActivity(intent);
        }
        else if(item.getItemId()==R.id.idAllUser)
        {
            Intent intent=new Intent(this,AllUser.class);
            startActivity(intent);
        }
        return true;
    }
}
