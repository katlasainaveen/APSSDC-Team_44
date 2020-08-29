package com.example.recipeapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class Signup_page extends AppCompatActivity {

    TextInputLayout fullname, email, phone, password;
    Button signup_btn;
    ImageButton back_btn;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup_page);

        fullname = findViewById(R.id.fullname_txt);
        email = findViewById(R.id.email_txt);
        phone = findViewById(R.id.phone_txt);
        password = findViewById(R.id.password_txt);

        back_btn = findViewById(R.id.back_btn);
        back_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        progressBar = findViewById(R.id.progress_bar2);
        progressBar.setVisibility(View.INVISIBLE);

        signup_btn = findViewById(R.id.signup_btn2);
        signup_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                final String full_name = StringUtils.capitalize(fullname.getEditText().getText().toString().trim());
                final String em_ail = email.getEditText().getText().toString().trim();
                final String ph_one = phone.getEditText().getText().toString().trim();
                String pass_word = password.getEditText().getText().toString();

                if (full_name.isEmpty() | em_ail.isEmpty() | ph_one.isEmpty() | pass_word.isEmpty()) {
                    Toast.makeText(Signup_page.this, "Required Details are Missing", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (validateemail() == false) {
                    Toast.makeText(Signup_page.this, "Use SRM mail ID only", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (validatecontact(ph_one) == false) {
                    Toast.makeText(Signup_page.this, "Invalid Emer Contact Number", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else if (validatepassword() == false) {
                    Toast.makeText(Signup_page.this, "Please choose a stronger Password", Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.INVISIBLE);
                } else {
                    mAuth.createUserWithEmailAndPassword(em_ail, pass_word)
                            .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                                @Override
                                public void onSuccess(AuthResult authResult) {
                                    Map<String, Object> user = new HashMap<>();
                                    user.put("Full Name", full_name);
                                    user.put("Email", em_ail);
                                    user.put("Phone Number", ph_one);
                                    FirebaseFirestore.getInstance().collection("Users").document(mAuth.getCurrentUser().getUid())
                                            .set(user)
                                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                                @Override
                                                public void onSuccess(Void aVoid) {
                                                    Alert_message am = new Alert_message(2, "Success", "Login to use ChefLab using your details");
                                                    am.show(getSupportFragmentManager(), "Success");
                                                }
                                            })
                                            .addOnFailureListener(new OnFailureListener() {
                                                @Override
                                                public void onFailure(@NonNull Exception e) {
                                                    progressBar.setVisibility(View.INVISIBLE);
                                                    Toast.makeText(Signup_page.this, "Failed, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                                }
                                            });
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    progressBar.setVisibility(View.INVISIBLE);
                                    Toast.makeText(Signup_page.this, "Error Occured, " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            });
                }
            }
        });


    }

    private boolean validateemail() {
        String text = email.getEditText().getText().toString().trim();
        if (text.contains("@") && (text.contains("."))) {
            return true;
        }
        return false;
    }

    private boolean validatecontact(String num) {
        if (num.length() == 10) {
            return true;
        }
        return false;
    }

    private boolean validatepassword() {
        String textpass = password.getEditText().getText().toString().trim();
        final Pattern pass_pattern = Pattern.compile("^" +
                "(?=.*[0-9])" +         //at least 1 digit
                "(?=.*[a-z])" +         //at least 1 lower case letter
                "(?=.*[A-Z])" +         //at least 1 upper case letter
                "(?=.*[@#$%^&+=])" +    //at least 1 special character
                ".{8,}" +               //at least 8 characters
                "$");

        if (!pass_pattern.matcher(textpass).matches()) {
            return false;
        }
        return true;
    }


}