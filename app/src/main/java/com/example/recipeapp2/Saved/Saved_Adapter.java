package com.example.recipeapp2.Saved;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.ShareActionProvider;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.recipeapp2.Breakfest.Breakfast;
import com.example.recipeapp2.Breakfest.Recycler_total;
import com.example.recipeapp2.R;
import com.example.recipeapp2.Saved_fragment;
import com.example.recipeapp2.Tap_item;

import java.util.ArrayList;
import java.util.List;

public class Saved_Adapter extends RecyclerView.Adapter<Saved_Adapter.SavedViewHolder> {

    Context saved_fragment;
    ArrayList<Recycler_total> recyclerTotalArrayList;

    public Saved_Adapter(Context saved_fragment, ArrayList<Recycler_total> recyclerTotalArrayList) {
        this.saved_fragment = saved_fragment;
        this.recyclerTotalArrayList = recyclerTotalArrayList;
    }

    @NonNull
    @Override
    public SavedViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_2, parent, false);
        return new SavedViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SavedViewHolder holder, int position) {
        Recycler_total model = recyclerTotalArrayList.get(position);
        holder.title.setText(model.getName());
        holder.details.setText(model.getDescription());
        holder.time.setText(model.getTotaltime());
        Glide.with(saved_fragment)
                .load(model.getImage_url_1())
                .into(holder.imageView);
        holder.ratingBar.setRating(model.getRating());
        holder.cooktime = model.getCooktime();
        holder.Procedure = model.getProcedure();
        holder.Notes = model.getNotes();
        holder.Nutrition = model.getNutrition();
        holder.PreparationTime = model.getPreparationTime();
        holder.image_url1 = model.getImage_url_1();
        holder.image_url2 = model.getImage_url_2();
        holder.key = model.getKey();
        holder.code = model.getCode();
        holder.rating_num = model.getRating();
        holder.Ingredients = model.getIngredients();



    }

    @Override
    public int getItemCount() {
        return recyclerTotalArrayList.size();
    }

    public class SavedViewHolder extends RecyclerView.ViewHolder {

        TextView title, details, time;
        RatingBar ratingBar;
        ImageView imageView;
        String description, cooktime, Procedure, Notes, Nutrition, PreparationTime, image_url1, image_url2, key, code;
        long rating_num;
        List<String> Ingredients;

        public SavedViewHolder(@NonNull View itemView) {
            super(itemView);
//            title = itemView.findViewById(R.id.title_here);
//            time = itemView.findViewById(R.id.totaltime_here);
//            details = itemView.findViewById(R.id.description_here);
//            ratingBar = itemView.findViewById(R.id.ratingBar_here);
//            imageView = itemView.findViewById(R.id.image_here1);
////            image2 = itemView.findViewById(R.id.image_here2);
            title = itemView.findViewById(R.id.recycler2_title);
            details = itemView.findViewById(R.id.recycler2_details);
            time = itemView.findViewById(R.id.recycler2_time);
            ratingBar = itemView.findViewById(R.id.recycler2_ratings);
            imageView = itemView.findViewById(R.id.recycler2_image);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int pos = getAdapterPosition();

                    if (pos != RecyclerView.NO_POSITION) {
                        Intent news = new Intent(saved_fragment, Tap_item.class);
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
                        saved_fragment.startActivity(news);

                    }
                }
            });

        }
    }
}














