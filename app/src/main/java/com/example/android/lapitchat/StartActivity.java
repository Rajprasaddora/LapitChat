package com.example.android.lapitchat;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class StartActivity extends AppCompatActivity {
    private Button reg_button;
    private Button log_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        reg_button=findViewById(R.id.startregbutton);
        log_button=findViewById(R.id.startlogbutton);
        reg_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,RegisterActivity.class);
                startActivity(intent);
                //finish();
            }
        });
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(StartActivity.this,loginActivity.class);
                startActivity(intent);
                //finish();
            }
        });

    }
}
