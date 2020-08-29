package com.example.recipeapp2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.recipeapp2.Breakfest.Recycler_total;
import com.example.recipeapp2.Saved.Saved_Adapter;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Saved_fragment extends Fragment {

    RecyclerView recyclerView;
    Saved_Adapter adapter;
    ArrayList<Recycler_total> recyclerTotalArrayList;
    List<String> ingredients;

    public static final String SHARED_PREFES = "sharedpreferences";
    public static final String UID = "UID";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.fragment_saved_fragment, container, false);

        recyclerView = v.findViewById(R.id.saved_recycler);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        recyclerTotalArrayList = new ArrayList<>();

        get_saved();


        return v;
    }

    public void get_saved() {
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(SHARED_PREFES, Context.MODE_PRIVATE);
        String uid = sharedPreferences.getString(UID, null);

        FirebaseFirestore.getInstance().collection("Users").document(uid).collection("Saved")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        recyclerTotalArrayList.clear();
                        if (error != null) {
                            Toast.makeText(getActivity(), "Error Occured: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentSnapshot doc : value.getDocuments()) {
                            String name = doc.getString("Name");
                            String notes = doc.getString("Notes");
                            String nutrition = doc.getString("Nutrition");
                            String preparationTime = doc.getString("PreparationTime");
                            String procedure = doc.getString("Procedure");
                            String cooktime = doc.getString("cooktime");
                            String totaltime = doc.getString("totaltime");
                            String image_url_1 = doc.getString("image_url_1");
                            String image_url_2 = doc.getString("image_url_2");
                            String code = doc.getString("code");
                            String key = doc.getId();
                            int rating = doc.getLong("Rating").intValue();
                            String description = doc.getString("Description");
                            ingredients = (List<String>) doc.get("Ingredients");

                            Recycler_total recycler_total = new Recycler_total(description, name, notes, ingredients, rating, nutrition, preparationTime, procedure, cooktime,
                                    totaltime, image_url_2, image_url_1, key, code);
                            recyclerTotalArrayList.add(recycler_total);

                        }
                        adapter = new Saved_Adapter(getActivity(), recyclerTotalArrayList);
                        recyclerView.setAdapter(adapter);


                    }
                });

//        for (final String a : aws2) {
//            FirebaseFirestore.getInstance().collection("Users").document(uid).collection(a)
//                    .get()
//                    .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                        @Override
//                        public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                            if (task.isSuccessful()) {
//                                for (DocumentSnapshot documentSnapshot : task.getResult()) {
//                                    String doc_name = documentSnapshot.getId();
//
//                                    FirebaseFirestore.getInstance().collection(a).document(doc_name)
//                                            .get()
//                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    if (task.isSuccessful()) {
//                                                        DocumentSnapshot doc = task.getResult();
//                                                        String name = doc.getString("Name");
//                                                        String notes = doc.getString("Notes");
//                                                        String nutrition = doc.getString("Nutrition");
//                                                        String preparationTime = doc.getString("PreparationTime");
//                                                        String procedure = doc.getString("Procedure");
//                                                        String cooktime = doc.getString("cooktime");
//                                                        String totaltime = doc.getString("totaltime");
//                                                        String image_url_1 = doc.getString("image_url_1");
//                                                        String image_url_2 = doc.getString("image_url_2");
//                                                        String code = doc.getString("code");
//                                                        String key = doc.getId();
////                                                        long rating = doc.getLong("Rating").intValue();
//                                                        String description = doc.getString("Description");
//                                                        ingredients = (List<String>) doc.get("Ingredients");
//
//                                                        Recycler_total recycler_total = new Recycler_total(description, name, notes, ingredients, 3, nutrition, preparationTime, procedure, cooktime,
//                                                                totaltime, image_url_2, image_url_1, key, code);
//                                                        recyclerTotalArrayList.add(recycler_total);
//
//                                                        adapter = new Saved_Adapter(getContext(), recyclerTotalArrayList);
//                                                        recyclerView.setAdapter(adapter);
//                                                    }
//                                                }
//                                            });
//                                            .addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onEvent(@Nullable DocumentSnapshot doc, @Nullable FirebaseFirestoreException error) {
//                                                    
//                                                }
//                                            });

//                                            .get()
//                                            .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//                                                @Override
//                                                public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//                                                    if (task.isSuccessful()) {
//
//                                                    } else {
//                                                        Toast.makeText(getActivity(), "Error Occured.", Toast.LENGTH_SHORT).show();
//                                                    }
//                                                }
//                                            });


//                                }
//                            }
//                        }
//
//                    });
//        }
    }


}

//for (final String a : aws2) {
//        FirebaseFirestore.getInstance().collection("Users").document(uid).collection(a)
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//@Override
//public void onComplete(@NonNull Task<QuerySnapshot> task) {
//        if (task.isSuccessful()) {
//        for (DocumentSnapshot documentSnapshot : task.getResult()) {
//        String doc_name = documentSnapshot.getId();
//
//        FirebaseFirestore.getInstance().collection(a).document(doc_name)
//
//
//        .get()
//        .addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
//@Override
//public void onComplete(@NonNull Task<DocumentSnapshot> task) {
//        if (task.isSuccessful()) {
//        DocumentSnapshot doc = task.getResult();
//        String name = doc.getString("Name");
//        String notes = doc.getString("Notes");
//        String nutrition = doc.getString("Nutrition");
//        Toast.makeText(getActivity(), "" + nutrition, Toast.LENGTH_SHORT).show();
//        String preparationTime = doc.getString("PreparationTime");
//        String procedure = doc.getString("Procedure");
//        String cooktime = doc.getString("cooktime");
//        String totaltime = doc.getString("totaltime");
//        String image_url_1 = doc.getString("image_url_1");
//        String image_url_2 = doc.getString("image_url_2");
//        String code = doc.getString("code");
//        String key = doc.getId();
//        long rating = doc.getLong("Rating");
//        String description = doc.getString("Description");
//        ingredients = (List<String>) doc.get("Ingredients");
//
//        Recycler_total recycler_total = new Recycler_total(description, name, notes, ingredients, rating, nutrition, preparationTime, procedure, cooktime,
//        totaltime, image_url_2, image_url_1, key, code);
//        recyclerTotalArrayList.add(recycler_total);
//
//        adapter = new Saved_Adapter(getContext(), recyclerTotalArrayList);
//        recyclerView.setAdapter(adapter);
//        } else {
//        Toast.makeText(getActivity(), "Error Occured.", Toast.LENGTH_SHORT).show();
//        }
//        }
//        });
//
//
//        }
//        }
//        }
//
//        });
//
//
//
//
//
//
//
//
//
//
//
