package com.example.foodsharing1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.appcompat.widget.Toolbar;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.rengwuxian.materialedittext.MaterialEditText;

import java.util.HashMap;

public class registerActivity extends AppCompatActivity {
    ProgressDialog progressDialog;
    MaterialEditText username,password,email,
            lastname,mobile_num,
            gender,dob,
            street,landmark,city;
    Button register;

    CheckBox agree;
  //  CountryCodePicker ccp;
    AppCompatEditText edtPhoneNumber;

    FirebaseAuth auth;
    DatabaseReference reference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Register");
        setSupportActionBar(myToolbar);


      //  ccp = (CountryCodePicker) findViewById(R.id.ccp);
        edtPhoneNumber = (AppCompatEditText) findViewById(R.id.mobile_number);
        //ccp.registerPhoneNumberTextView(edtPhoneNumber);
        username=findViewById(R.id.username);
        lastname=findViewById(R.id.lastname);
        // mobile_num=findViewById(R.id.mobile_number);

        agree=findViewById(R.id.agree);

        street=findViewById(R.id.street);
        city=findViewById(R.id.city);
        landmark=findViewById(R.id.landmark);

        password=findViewById(R.id.password);
        email=findViewById(R.id.email);
        register=findViewById(R.id.btn_register);
         progressDialog=new ProgressDialog(this);
        progressDialog.setMessage("Saving..");

        auth=FirebaseAuth.getInstance();


        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String txt_username=username.getText().toString();
                // String txt_lastname=lastname.getText().toString();
                String txt_mobile_number=edtPhoneNumber.getText().toString();

                String txt_street=street.getText().toString();
                String txt_city=city.getText().toString();
                // String txt_landmark=landmark.getText().toString();
                //String txt_dob=dob.getText().toString();
                String txt_email=email.getText().toString();
                String txt_password=password.getText().toString();
                final Boolean checkBoxState = agree.isChecked();

                if (TextUtils.isEmpty(txt_username)||TextUtils.isEmpty(txt_email)||TextUtils.isEmpty(txt_password))
                {
                    Toast.makeText(registerActivity.this,"All fields are required", Toast.LENGTH_SHORT).show();

                }
                else if(txt_password.length()<6){
                    Toast.makeText(registerActivity.this,"password must be at least 6 characters",Toast.LENGTH_SHORT).show();

                }
                else {
                    if(checkBoxState) {
                        registers(txt_username, txt_email, txt_password, txt_mobile_number, txt_city, txt_street);
                        progressDialog.show();

                    }
                        else
                        Toast.makeText(registerActivity.this,"Please Read the Terms and Conditions",Toast.LENGTH_SHORT).show();
                }

            }
        });


    }


    private void registers(final String username, String email, final String password,final String mob_num,final String city,final String street)
    {
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            FirebaseUser firebaseUser=auth.getCurrentUser();
                            assert firebaseUser!=null;
                            String userid=firebaseUser.getUid();

                            reference= FirebaseDatabase.getInstance().getReference("Users").child(userid);

                            HashMap<String,String> hashMap=new HashMap<>();
                            hashMap.put("id",userid);
                            hashMap.put("username",username);
                            hashMap.put("lastname","null");
                            hashMap.put("mobilenumber",mob_num);

                            hashMap.put("city",city);

                            hashMap.put("landmark","null");
                            hashMap.put("street",street);
                            //hashMap.put("status","offline");
                            //hashMap.put("search",username.toLowerCase());



                            reference.setValue(hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful())
                                    {
                                        progressDialog.dismiss();
                                        Intent intent=new Intent(registerActivity.this,loginActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK| intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);
                                        finish();

                                    }
                                }
                            });
                        }
                        else {
                            progressDialog.dismiss();
                            Toast.makeText(registerActivity.this,"You can't register with this email or password",Toast.LENGTH_SHORT).show();

                        }
                    }
                });
    }
}
