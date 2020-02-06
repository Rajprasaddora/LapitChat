package com.example.android.lapitchat;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.Slide;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import de.hdodenhof.circleimageview.CircleImageView;

public class settingActivity extends AppCompatActivity {

    String url;

    private static final int GALLERY_PICK=1;

    private TextView UserName;
    private  TextView Userstatus;
    private Button btnStatus;
    private Button btnImage;
    private CircleImageView profile;

    private FirebaseUser user;
    private DatabaseReference ref;
    StorageReference storageReference;
    private Toolbar toolbar;
    private StorageReference sref;

    private ProgressDialog mprogress;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getwindowanimation();
        setContentView(R.layout.activity_setting);

        toolbar=findViewById(R.id.setting_action_bar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Profile Setting");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        user= FirebaseAuth.getInstance().getCurrentUser();
        String uid=user.getUid();
        UserName=findViewById(R.id.IdName);
        profile=findViewById(R.id.idCircularImageView);
        Userstatus=findViewById(R.id.IdStatus);
        btnStatus=findViewById(R.id.IdChangeStatus);
        btnImage=findViewById(R.id.IdChangeImage);
        ref= FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
        sref= FirebaseStorage.getInstance().getReference();



        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                //Toast.makeText(settingActivity.this, dataSnapshot.getValue().toString(), Toast.LENGTH_SHORT).show();
                String name=dataSnapshot.child("name").getValue().toString();
                String image=dataSnapshot.child("image").getValue().toString();
                String status=dataSnapshot.child("status").getValue().toString();
                UserName.setText(name);
                Userstatus.setText(status);
                if(!image.equals("default")) {
                    Picasso.get().load(image).placeholder(R.drawable.profile).into(profile);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
        btnStatus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(settingActivity.this,StatusActivity.class);
                intent.putExtra("statusText",Userstatus.getText().toString());
                startActivity(intent);
            }
        });
        btnImage.setOnClickListener(new View.OnClickListener() {
            @Override
           public void onClick(View v) {
                Intent intent=new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent,"SELECT IMAGE"),GALLERY_PICK);

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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==GALLERY_PICK && resultCode==RESULT_OK)
        {
            Uri uri=data.getData();
            CropImage.activity(uri)
                    .setAspectRatio(1,1)
                    .start(settingActivity.this);
        }
        if(requestCode==CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE )
        {
            CropImage.ActivityResult result=CropImage.getActivityResult(data);
            if(resultCode==RESULT_OK)
            {
                mprogress=new ProgressDialog(settingActivity.this);
                mprogress.setTitle("Uploading Image");
                mprogress.setMessage("Please wait while image is being uploaded");
                mprogress.setCanceledOnTouchOutside(false);
                mprogress.show();

                Uri uri=result.getUri();
                //profile.setImageURI(uri);
                storageReference=sref.child("profile_image").child(System.currentTimeMillis()+".jpg");

                storageReference.putFile(uri).addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                       storageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                           @Override
                           public void onSuccess(Uri uri) {
                               String url=uri.toString();
                               ref.child("image").setValue(url).addOnSuccessListener(new OnSuccessListener<Void>() {
                                   @Override
                                   public void onSuccess(Void aVoid)
                                   {
                                       mprogress.dismiss();

                                       Toast.makeText(settingActivity.this, "Image Uploaded", Toast.LENGTH_SHORT).show();
                                   }
                               });
                           }
                       });

                    }
                });
            }
            else if(resultCode==CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
            {
                mprogress.dismiss();
                Toast.makeText(settingActivity.this, "Couldn't been uploaded", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
