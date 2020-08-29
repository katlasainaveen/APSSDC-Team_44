package com.example.recipeapp2;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile_ extends Fragment {

    TextView name, emaill, phonee;
    Button logout, add;

    public static final String SHARED_PREFES = "sharedpreferences";
    public static final String CanAdd = "canadd";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment


        View v = inflater.inflate(R.layout.fragment_profile, container, false);

        add = v.findViewById(R.id.add_recipe);
        add.setEnabled(false);
        add.setVisibility(View.INVISIBLE);

        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFES, Context.MODE_PRIVATE);
        final Boolean canadd = sharedPreferences.getBoolean(CanAdd, false);

        if (canadd) {
            add.setEnabled(true);
            add.setVisibility(View.VISIBLE);
        }

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Add_Recipe.class);
                startActivity(intent);
            }
        });

        name = v.findViewById(R.id.name_label);
        emaill = v.findViewById(R.id.email_label);
        phonee = v.findViewById(R.id.phone_label);

        FirebaseFirestore.getInstance().collection("Users").document(FirebaseAuth.getInstance().getCurrentUser().getUid())
                .get()
                .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            String nam = document.getString("Full Name");
                            String email = document.getString("Email");
                            String phone = document.getString("Phone Number");

                            name.setText(nam);
                            emaill.setText(email);
                            phonee.setText(phone);

                        } else {
                            Toast.makeText(getActivity(), "Error Occured, " + task.getException(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });

        logout = v.findViewById(R.id.logout_btn_);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FirebaseAuth.getInstance().signOut();
                Intent i = new Intent(getActivity(), Login_screnn.class);
                ActivityCompat.finishAffinity(getActivity());
                startActivity(i);
            }
        });


        return v;
    }


}