package com.example.recipeapp2;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;


import com.bumptech.glide.Glide;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Tap_item extends AppCompatActivity {

    TextView title, totaltime, preparationtime, cooktime, notes, nutrients, description, ingredients, procedure;
    RatingBar rating;
    ImageButton back, save;
    ImageView imageView1, imageView2;

    public static final String SHARED_PREFES = "sharedpreferences";
    public static final String UID = "UID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tap_item);

//        ingredients = findViewById(R.id.recycler_ingredients);

        title = findViewById(R.id.tap_title);
        totaltime = findViewById(R.id.tap_totaltime);
        preparationtime = findViewById(R.id.tap_preparationtime);
        cooktime = findViewById(R.id.tap_cooktime);
        notes = findViewById(R.id.tap_notes);
        nutrients = findViewById(R.id.tap_nutrition);
        description = findViewById(R.id.tap_description);
        procedure = findViewById(R.id.tap_procedure);
        rating = findViewById(R.id.tap_ratingBar);
        imageView1 = findViewById(R.id.tap_image1);
        imageView2 = findViewById(R.id.tap_image2);
        ingredients = findViewById(R.id.tap_ingredients);

        back = findViewById(R.id.back_btn2);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        save = findViewById(R.id.tap_save);
        setcolor();

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, Context.MODE_PRIVATE);
                final String uid = sharedPreferences.getString(UID, null);
                final String key = getIntent().getStringExtra("key");

                FirebaseFirestore.getInstance().collection("Users").document(uid).collection("Saved").document(key)
                        .get()
                        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                            @Override
                            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                if (task.isSuccessful()) {
                                    DocumentSnapshot document = task.getResult();
                                    if (document.exists()) {
                                        document.getReference().delete();
                                        Toast.makeText(Tap_item.this, "Unsaved.", Toast.LENGTH_SHORT).show();
                                        save.setColorFilter(ContextCompat.getColor(Tap_item.this, R.color.colorPrimary), PorterDuff.Mode.MULTIPLY);
                                    }
                                    else {

                                        final Map<String, Object> data1 = new HashMap<>();
                                        data1.put("Name", getIntent().getStringExtra("name"));
                                        data1.put("Notes", getIntent().getStringExtra("notes"));
                                        data1.put("Nutrition", getIntent().getStringExtra("nutrients"));
                                        data1.put("PreparationTime", getIntent().getStringExtra("preparationtime"));
                                        data1.put("Procedure", getIntent().getStringExtra("procedure"));
                                        data1.put("cooktime", getIntent().getStringExtra("cooktime"));
                                        data1.put("totaltime", getIntent().getStringExtra("totaltime"));
                                        data1.put("image_url_1", getIntent().getStringExtra("image1"));
                                        data1.put("image_url_2", getIntent().getStringExtra("image2"));
                                        data1.put("code", getIntent().getStringExtra("code"));
                                        data1.put("Rating", getIntent().getIntExtra("rating", 0));
                                        data1.put("Description", getIntent().getStringExtra("description"));
                                        data1.put("Ingredients", getIntent().getStringArrayListExtra("ingredients"));
                                        data1.put("key", getIntent().getStringExtra("key"));

                                        FirebaseFirestore.getInstance().collection("Users").document(uid).collection("Saved").document(key)
                                                .set(data1)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if (task.isSuccessful()) {
                                                            Toast.makeText(Tap_item.this, "Done. Saved", Toast.LENGTH_SHORT).show();
                                                            save.setColorFilter(ContextCompat.getColor(Tap_item.this, R.color.duskYellow), PorterDuff.Mode.MULTIPLY);
                                                        }
                                                    }
                                                })
                                                .addOnFailureListener(new OnFailureListener() {
                                                    @Override
                                                    public void onFailure(@NonNull Exception e) {
                                                        Toast.makeText(Tap_item.this, "Error Occured, Try Again", Toast.LENGTH_SHORT).show();
                                                    }
                                                });
                                    }
                                } else {
                                    Toast.makeText(Tap_item.this, "Error Occured, Try Again", Toast.LENGTH_SHORT).show();
                                }
                            }
                        });

            }


        });

        title.setText(getIntent().getStringExtra("name"));
        totaltime.setText(getIntent().getStringExtra("totaltime"));
        preparationtime.setText(getIntent().getStringExtra("preparationtime"));
        cooktime.setText(getIntent().getStringExtra("cooktime"));
        notes.setText(getIntent().getStringExtra("notes"));
        nutrients.setText(getIntent().getStringExtra("nutrients"));
        description.setText(getIntent().getStringExtra("description"));
        procedure.setText(getIntent().getStringExtra("procedure"));
        rating.setRating(getIntent().getIntExtra("rating", 0));

        String a = "";
        ArrayList<String> test = getIntent().getStringArrayListExtra("ingredients");
        for (int i = 0; i < test.size(); i++) {
            a = a + "\n" + test.get(i);
        }
        ingredients.setText(a);

        Glide.with(Tap_item.this)
                .load(getIntent().getStringExtra("image1"))
                .into(imageView1);
        Glide.with(Tap_item.this)
                .load(getIntent().getStringExtra("image2"))
                .into(imageView2);
    }

    public void setcolor() {
        SharedPreferences sharedPreferences = getSharedPreferences(SHARED_PREFES, Context.MODE_PRIVATE);
        final String uid = sharedPreferences.getString(UID, null);
        final String key = getIntent().getStringExtra("key");
        final String code = getIntent().getStringExtra("code");

        FirebaseFirestore.getInstance().collection("Users").document(uid).collection("Saved").document(key)
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document.exists()) {
                                save.setColorFilter(ContextCompat.getColor(Tap_item.this, R.color.duskYellow), android.graphics.PorterDuff.Mode.MULTIPLY);
                            }
                            else {
                                save.setColorFilter(ContextCompat.getColor(Tap_item.this, R.color.colorPrimary), android.graphics.PorterDuff.Mode.MULTIPLY);
                            }
                        } else {
                            Toast.makeText(Tap_item.this, "Error Occured, Try Again", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


}














