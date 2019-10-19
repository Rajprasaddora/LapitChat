package com.example.android.lapitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    private TextInputLayout name;
    private TextInputLayout email;
    private TextInputLayout password;
    private Button sign_in;
    private Toolbar toolbar;
    private DatabaseReference curr_user;
    private ProgressDialog mProgress;
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        mAuth=FirebaseAuth.getInstance();

        mProgress=new ProgressDialog(this);

        toolbar=findViewById(R.id.reg_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create Account");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=findViewById(R.id.textName);
        email=findViewById(R.id.emailText);
        password=findViewById(R.id.passwordText);
        sign_in=findViewById(R.id.signinButton);
        sign_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String UsersName=name.getEditText().getText().toString();
                String UsersEmail=email.getEditText().getText().toString();
                String UsersPassword=password.getEditText().getText().toString();
                if(!TextUtils.isEmpty(UsersName) || !TextUtils.isEmpty(UsersEmail) || !TextUtils.isEmpty(UsersPassword))
                {
                    mProgress.setTitle("Registring User");
                    mProgress.setMessage("Please wait while creating user's Account");
                    mProgress.setCanceledOnTouchOutside(false);
                    mProgress.show();
                    createAccount(UsersName,UsersEmail,UsersPassword);
                }

            }
        });
    }
    private void createAccount(final String name, String usersEmail, String userPassword)
    {
         mAuth.createUserWithEmailAndPassword(usersEmail,userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
        @Override
        public void onComplete(@NonNull Task<AuthResult> task) {
            if(task.isSuccessful())
            {
                FirebaseUser user=mAuth.getCurrentUser();
                String uid=user.getUid();
                curr_user= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                HashMap<String,String>userMap=new HashMap<>();
                userMap.put("name",name);
                userMap.put("status","Hi There ! I am using Lapit Chat App");
                userMap.put("image","default");
                curr_user.setValue((userMap)).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            mProgress.dismiss();
                            Intent intent=new Intent(RegisterActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                });

            }
            else
            {
                mProgress.hide();
                Toast.makeText(RegisterActivity.this,task.getException().toString(),Toast.LENGTH_LONG).show();
            }
        }
    });
    }
}
