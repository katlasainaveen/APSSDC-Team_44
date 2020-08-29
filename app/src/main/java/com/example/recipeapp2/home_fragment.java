package com.example.recipeapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipeapp2.Breakfest.Breakfast;
import com.example.recipeapp2.Breakfest.Recycler_total;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class home_fragment extends Fragment {

    RecyclerView feature_1;
    RecyclerView feature_2;
    FirestoreRecyclerAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.home_fragment, container, false);

        feature_1 = v.findViewById(R.id.recycler_1);


        recycler1();

//        getdetails();

        return v;
    }


    public void recycler1() {

        feature_1.setHasFixedSize(true);
        feature_1.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

        Query query = FirebaseFirestore.getInstance().collection("Breakfast");
        FirestoreRecyclerOptions<Recycler_total> options = new FirestoreRecyclerOptions.Builder<Recycler_total>()
                .setQuery(query, Recycler_total.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Recycler_total, Breakfast2ViewHolder>(options) {
            @NonNull
            @Override
            public Breakfast2ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_1, parent, false);
                return new Breakfast2ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull Breakfast2ViewHolder holder, int position, @NonNull Recycler_total model) {
                holder.title.setText(model.getName());
                holder.time.setText(model.getTotaltime());
                holder.rating.setRating(model.getRating());
                Glide.with(getActivity())
                        .load(model.getImage_url_1())
                        .into(holder.image1);

                holder.description = model.getDescription();
                holder.cooktime = model.getCooktime();
                holder.PreparationTime = model.getPreparationTime();
                holder.Procedure = model.getProcedure();
                holder.Notes = model.getNotes();
                holder.Nutrition = model.getNutrition();
                holder.rating_num = model.getRating();
                holder.image_url1 = model.getImage_url_1();
                holder.image_url2 = model.getImage_url_2();
                holder.Ingredients = model.getIngredients();
                holder.key = model.getKey();
                holder.code = model.getCode();
            }
        };

        feature_1.setHasFixedSize(true);
        feature_1.setAdapter(adapter);

    }



    private class Breakfast2ViewHolder extends RecyclerView.ViewHolder {

        TextView title, time;
        RatingBar rating;
        ImageView image1;
        String description, cooktime, Procedure, Notes, Nutrition, PreparationTime, image_url1, image_url2, key, code;
        Boolean saved;
        long rating_num;
        List<String> Ingredients;

        public Breakfast2ViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.recycler1_title);
            time = itemView.findViewById(R.id.recycler1_time);
            rating = itemView.findViewById(R.id.recycler1_ratings);
            image1 = itemView.findViewById(R.id.recycler1_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        Intent news = new Intent(getActivity(), Tap_item.class);
                        news.putExtra("name", title.getText().toString());
                        news.putExtra("totaltime", time.getText().toString());
                        news.putExtra("preparationtime", PreparationTime);
                        news.putExtra("cooktime", cooktime);
                        news.putExtra("notes", Notes);
                        news.putExtra("nutrients", Nutrition);
                        news.putExtra("description", description);
                        news.putExtra("procedure", Procedure);
                        news.putExtra("image1", image_url1);
                        news.putExtra("image2", image_url2);
                        news.putExtra("rating", rating_num);
                        news.putExtra("key", key);
                        news.putExtra("code", code);
                        ArrayList<String> arrayList = new ArrayList<>(Ingredients);
                        news.putStringArrayListExtra("ingredients", arrayList);
                        startActivity(news);

                    }
                }
            });
        }
    }



    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

//    public void getdetails() {
//        FirebaseFirestore.getInstance().collection("Dinner")
//                .get()
//                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
//                    @Override
//                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
//                        if (task.isSuccessful()) {
//                            for (DocumentSnapshot doc : task.getResult()) {
//
//
//
//                                FirebaseFirestore.getInstance().collection("All").document(key).set(data1);
//
//                            }
//                        }
//                    }
//                });
//    }

}







































