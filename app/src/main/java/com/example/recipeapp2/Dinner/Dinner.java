package com.example.recipeapp2.Dinner;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.recipeapp2.Breakfest.Breakfast;
import com.example.recipeapp2.Breakfest.Recycler_total;
import com.example.recipeapp2.Lunch.Lunch;
import com.example.recipeapp2.R;
import com.example.recipeapp2.Tap_item;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

import java.util.ArrayList;
import java.util.List;

public class Dinner extends AppCompatActivity {

    ImageButton back;
    RecyclerView recycler;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner);

        back = findViewById(R.id.back_btn_dinner);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        recycler = findViewById(R.id.dinner_recycler);
        recycler.setLayoutManager(new LinearLayoutManager(Dinner.this));

        Query query = FirebaseFirestore.getInstance().collection("Dinner");
        FirestoreRecyclerOptions<Recycler_total> options = new FirestoreRecyclerOptions.Builder<Recycler_total>()
                .setQuery(query, Recycler_total.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<Recycler_total, DinnerViewHolder>(options) {
            @NonNull
            @Override
            public DinnerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_breakfast, parent, false);
                return new DinnerViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull DinnerViewHolder holder, int position, @NonNull Recycler_total model) {
                holder.title.setText(model.getName());
                holder.description.setText(model.getDescription());
                holder.time.setText(model.getTotaltime());
                holder.rating.setRating(model.getRating());
                Glide.with(Dinner.this)
                        .load(model.getImage_url_1())
                        .into(holder.image1);

                Glide.with(Dinner.this)
                        .load(model.getImage_url_2())
                        .into(holder.image2);

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

        recycler.setHasFixedSize(true);
        recycler.setAdapter(adapter);


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

    private class DinnerViewHolder extends RecyclerView.ViewHolder {

        TextView title, time, description;
        RatingBar rating;
        ImageView image1, image2;
        String cooktime, Procedure, Notes, Nutrition, PreparationTime, image_url1, image_url2, key, code;
        int rating_num;
        List<String> Ingredients;

        public DinnerViewHolder(@NonNull View itemView) {
            super(itemView);

            title = itemView.findViewById(R.id.title_here);
            time = itemView.findViewById(R.id.totaltime_here);
            description = itemView.findViewById(R.id.description_here);
            rating = itemView.findViewById(R.id.ratingBar_here);
            image1 = itemView.findViewById(R.id.image_here1);
            image2 = itemView.findViewById(R.id.image_here2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        Intent news = new Intent(Dinner.this, Tap_item.class);
                        news.putExtra("name", title.getText().toString());
                        news.putExtra("totaltime", time.getText().toString());
                        news.putExtra("preparationtime", PreparationTime);
                        news.putExtra("cooktime", cooktime);
                        news.putExtra("notes", Notes);
                        news.putExtra("nutrients", Nutrition);
                        news.putExtra("description", description.getText().toString());
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


}