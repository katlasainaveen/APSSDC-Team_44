package com.example.recipeapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
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

import java.io.Serializable;

public class Login_screnn extends AppCompatActivity {

    TextInputLayout username, password;
    Button login, signup, forgotpass;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressBar progressBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screnn);

        username = findViewById(R.id.username_txt);
        password = findViewById(R.id.password_txt);

        progressBar = findViewById(R.id.progress_bar);
        progressBar.setVisibility(View.INVISIBLE);

        login = findViewById(R.id.login_btn);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String email_text = username.getEditText().getText().toString().trim();
                String pass_text = password.getEditText().getText().toString().trim();

                if (email_text.isEmpty() | pass_text.isEmpty()) {
                    progressBar.setVisibility(View.INVISIBLE);
                    Toast.makeText(Login_screnn.this, "Required Details are Missing", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.signInWithEmailAndPassword(email_text, pass_text)
                            .addOnCompleteListener(Login_screnn.this, new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()) {
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
                                                            ActivityCompat.finishAffinity(Login_screnn.this);
                                                            Intent welcome = new Intent(Login_screnn.this, Welcome_Screen.class);
                                                            welcome.putExtra("UID", uid);
                                                            welcome.putExtra("Name", name);
                                                            welcome.putExtra("Email", email);
                                                            welcome.putExtra("Phone", phone);
                                                            startActivity(welcome);

                                                        }
                                                        else {
                                                            progressBar.setVisibility(View.INVISIBLE);
                                                            Toast.makeText(Login_screnn.this, "Error Occured, " + task.getException(), Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });
                                    } else {
                                        progressBar.setVisibility(View.INVISIBLE);
                                        Toast.makeText(Login_screnn.this, "Incorrect Username or Password", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }
            }
        });

        signup = findViewById(R.id.signup_btn);
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sign_up = new Intent(Login_screnn.this, Signup_page.class);
                startActivity(sign_up);
            }
        });

        forgotpass = findViewById(R.id.forgot_password);
        forgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email_text = username.getEditText().getText().toString().trim();
                if (email_text.isEmpty()) {
                    Toast.makeText(Login_screnn.this, "Enter Email ID", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.sendPasswordResetEmail(email_text).addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            Toast.makeText(Login_screnn.this, "Success.Please Check your mail", Toast.LENGTH_SHORT).show();
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(Login_screnn.this, "Error Occurred: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }
}