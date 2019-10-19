package com.example.android.lapitchat;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class StatusActivity extends AppCompatActivity {
    private Toolbar mtoolbar;
    private DatabaseReference ref;
    private FirebaseUser user;
    private Button save;
    private ProgressDialog mProgress;
    private String text;
    private TextInputLayout status;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_status);
        mtoolbar=findViewById(R.id.status_action_bar);
        mtoolbar.setTitle("Status");
        setSupportActionBar(mtoolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        ref= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        status=findViewById(R.id.IdStatusText);
        text=getIntent().getStringExtra("statusText");
        status.getEditText().setText(text);

        save=findViewById(R.id.msave);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgress=new ProgressDialog(StatusActivity.this);
                mProgress.setTitle("Saving Changes");
                mProgress.setMessage("Please wait while changes are saved");
                mProgress.show();
                ref.child("status").setValue(status.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful())
                        {
                            //Toast.makeText(StatusActivity.this, task.getResult().toString(), Toast.LENGTH_LONG).show();
                            mProgress.dismiss();
                        }
                        else
                        {
                            mProgress.hide();
                            Toast.makeText(StatusActivity.this, "some error", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });


    }
}
