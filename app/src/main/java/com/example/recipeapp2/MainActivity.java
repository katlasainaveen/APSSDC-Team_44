package com.example.recipeapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static final String SHARED_PREFES = "sharedpreferences";
    public static final String Name = "Name";
    public static final String Contact = "Contact";
    public static final String UID = "UID";
    public static final String Email = "Email";
    public static final String CanAdd = "canadd";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (mAuth.getCurrentUser() == null) {
            Intent i = new Intent(MainActivity.this, Login_screnn.class);
            startActivity(i);
        }
        else {
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {

                    FirebaseFirestore.getInstance().collection("Users").document(mAuth.getCurrentUser().getUid())
                            .get()
                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                @Override
                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                    if (task.isSuccessful()) {
                                        DocumentSnapshot document = task.getResult();
                                        String uid = document.getId();
                                        String name = document.getString("Full Name");
                                        String email = document.getString("Email");
                                        String phone = document.getString("Phone Number");
                                        Boolean canadd = document.getBoolean("Can_Add");

                                        ActivityCompat.finishAffinity(MainActivity.this);
                                        Intent welcome = new Intent(MainActivity.this, Home.class);

                                        savedata(Name, name);
                                        savedata(Contact, phone);
                                        savedata(UID, uid);
                                        savedata(Email, email);
                                        savedata2(CanAdd, canadd);

                                        welcome.putExtra("UID", uid);
                                        welcome.putExtra("Name", name);
                                        welcome.putExtra("Email", email);
                                        welcome.putExtra("Phone", phone);
                                        welcome.putExtra("CanAdd", canadd);

                                        startActivity(welcome);
                                    }
                                    else {
                                        Toast.makeText(MainActivity.this, "Error Occured, " + task.getException(), Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }, 4000);
        }

        blink();

    }

    private void blink(){
        final Handler handler = new Handler();
        new Thread(new Runnable() {
            @Override
            public void run() {
                int timeToBlink = 500;    //in milissegunds
                try{Thread.sleep(timeToBlink);}catch (Exception e) {}
                handler.post(new Runnable() {
                    @Override
                    public void run() {
                        TextView txt = (TextView) findViewById(R.id.getting_started2);
                        if(txt.getVisibility() == View.VISIBLE){
                            txt.setVisibility(View.INVISIBLE);
                        }else{
                            txt.setVisibility(View.VISIBLE);
                        }
                        blink();
                    }
                });
            }
        }).start();
    }

    public void savedata(String s, String s1) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(s, s1);
        editor.commit();
    }

    public void savedata2(String s, Boolean s1) {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(s, s1);
        editor.commit();
    }



}