package com.example.android.lapitchat;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;


public class loginActivity extends AppCompatActivity {
    private TextInputLayout email;
    private Button log_button;
    private FirebaseAuth mAuth;
    private ProgressDialog mprogress;
    private TextInputLayout password;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mprogress=new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();

        toolbar=findViewById(R.id.log_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Log in");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        email=findViewById(R.id.idLogEmail);
        password=findViewById(R.id.idLogPassword);
        log_button=findViewById(R.id.idLogIn);
        log_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String Usersemail=email.getEditText().getText().toString();
                String UsersPassword=password.getEditText().getText().toString();
                mprogress.setTitle("Loging in ");
                mprogress.setMessage("Please wait while log in is under process");
                mprogress.show();
                login(Usersemail,UsersPassword);

            }
        });

    }
    private void login(String Usersemail,String UsersPassword)
    {
        mAuth.signInWithEmailAndPassword(Usersemail,UsersPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful())
                {
                    mprogress.dismiss();
                    Intent intent=new Intent(loginActivity.this,MainActivity.class);
                    startActivity(intent);
                    finish();
                }
                else
                {
                    mprogress.hide();
                    Toast.makeText(loginActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}
