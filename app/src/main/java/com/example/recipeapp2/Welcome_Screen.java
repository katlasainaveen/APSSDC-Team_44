package com.example.recipeapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.TextView;

import java.io.Serializable;

public class Welcome_Screen extends AppCompatActivity {

    TextView name;
    String nam, uid, email, phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome__screen);

        nam = getIntent().getStringExtra("Name");
        uid = getIntent().getStringExtra("UID");
        email = getIntent().getStringExtra("Email");
        phone = getIntent().getStringExtra("Phone");

        name = findViewById(R.id.name_txt);
        name.setText(nam);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                ActivityCompat.finishAffinity(Welcome_Screen.this);
                Intent i = new Intent(Welcome_Screen.this, Home.class);
                i.putExtra("UID", uid);
                i.putExtra("Name", nam);
                i.putExtra("Email", email);
                i.putExtra("Phone", phone);
                startActivity(i);
            }
        },4000);

    }



}