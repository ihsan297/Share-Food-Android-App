package com.example.foodsharing1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class chooseActivity extends AppCompatActivity {
    Button shrFood,getFood;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose);
        shrFood=findViewById(R.id.shrFood);
        getFood=findViewById(R.id.wantFood);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        myToolbar.setTitle("Select Option");
        setSupportActionBar(myToolbar);

        shrFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chooseActivity.this,shareFoodActivity.class);
                startActivity(intent);
            }
        });
        getFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(chooseActivity.this,foodTimelineActivity.class);
                startActivity(intent);
            }
        });

    }
}
