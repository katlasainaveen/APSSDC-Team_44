package com.example.recipeapp2;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.example.recipeapp2.Breakfest.Breakfast;
import com.example.recipeapp2.Dinner.Dinner;
import com.example.recipeapp2.Lunch.Lunch;
import com.example.recipeapp2.Snacks.Snacks;

public class category_fragment extends Fragment {

    Button breakfest, lunch, snacks, dinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v = inflater.inflate(R.layout.category_fragment, container, false);

        breakfest = v.findViewById(R.id.breakfest_category_btn);
        breakfest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Breakfast.class);
                startActivity(intent);
            }
        });

        lunch = v.findViewById(R.id.lunch_category_btn);
        lunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Lunch.class);
                startActivity(intent);
            }
        });

        dinner = v.findViewById(R.id.dinner_category_btn);
        dinner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Dinner.class);
                startActivity(intent);
            }
        });

        snacks = v.findViewById(R.id.snacks_category_btn);
        snacks.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), Snacks.class);
                startActivity(intent);
            }
        });
















        return v;
    }









}