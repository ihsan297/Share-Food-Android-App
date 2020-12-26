package com.example.foodsharing1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;
import com.theartofdev.edmodo.cropper.CropImage;

import java.util.HashMap;

public class shareFoodActivity extends AppCompatActivity {
    Uri imageUri;
    EditText foodName,foodLocation,foodDesc;
    Button postFood,upload;
    StorageReference fileRef;
    boolean temp;
    String imageUrl;
    ImageView imageAdded;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_share_food);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Create Food Post");
        setSupportActionBar(myToolbar);

        postFood=findViewById(R.id.postFood);
        foodName=findViewById(R.id.foodName);
        foodLocation=findViewById(R.id.foodLocation);
        imageAdded=findViewById(R.id.foogImg);
        foodDesc=findViewById(R.id.act_foodDesc);
        temp=false;

        upload=findViewById(R.id.upload);
        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                temp=true;
                openImage();

            }
        });
        postFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(temp){
                    uploadData();
                }
                else{
                    Toast.makeText(shareFoodActivity.this,"Open Image first",Toast.LENGTH_LONG).show();
                }

            }
        });
    }

    private void openImage() {
        Intent intent=new Intent();
        intent.setType("image/");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent,2);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 2 && resultCode == RESULT_OK){

            imageUri = data.getData();

            imageAdded.setImageURI(imageUri);
        } else {
            Toast.makeText(this, "Try again!", Toast.LENGTH_SHORT).show();

        }
    }


    private void uploadData( ) {
        final ProgressDialog pd=new ProgressDialog(this);
        pd.setMessage("Uploading...");
        pd.show();
        if (imageUri != null){
            final StorageReference filePath = FirebaseStorage.getInstance().getReference("Posts").child(foodName.getText().toString() + "." + getExtension(imageUri));

            StorageTask uploadtask = filePath.putFile(imageUri);
            uploadtask.continueWithTask(new Continuation() {
                @Override
                public Object then(@NonNull Task task) throws Exception {
                    if (!task.isSuccessful()){
                        throw task.getException();
                    }

                    return filePath.getDownloadUrl();
                }
            }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {
                    Uri downloadUri = task.getResult();
                    imageUrl = downloadUri.toString();

                    DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Posts");
                    String postId = ref.push().getKey();

                    HashMap<String , Object> map = new HashMap<>();
                    map.put("postid" , postId);
                    map.put("imageurl" , foodName.getText().toString()+"."+getExtension(imageUri));
                    map.put("foodname" , foodName.getText().toString());
                    map.put("foodloc",foodLocation.getText().toString());
                    map.put("fooddesc",foodDesc.getText().toString());
                    map.put("userid" , FirebaseAuth.getInstance().getCurrentUser().getUid());


                    ref.child(postId).setValue(map);


                    pd.dismiss();
                    startActivity(new Intent(shareFoodActivity.this , foodTimelineActivity.class));
                    finish();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(shareFoodActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "No image was selected!", Toast.LENGTH_SHORT).show();
        }

    }

    private String getExtension(Uri imageUri) {
        ContentResolver contentResolver=getContentResolver();
        MimeTypeMap mimeTypeMap=MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(contentResolver.getType(imageUri));
    }
}
