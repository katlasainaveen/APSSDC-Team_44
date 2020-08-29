package com.example.recipeapp2;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

public class Home extends AppCompatActivity {

    String nam, uid, email, phone;
    ChipNavigationBar chipNavigationBar;

    FragmentTransaction ft;

    home_fragment home_fragmentt;
    category_fragment category_fragmentt;
    Saved_fragment saved_fragmentt;
    profile_ profile_t;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        nam = getIntent().getStringExtra("Name");
        uid = getIntent().getStringExtra("UID");
        email = getIntent().getStringExtra("Email");
        phone = getIntent().getStringExtra("Phone");

        chipNavigationBar = findViewById(R.id.chipbar);
        chipNavigationBar.setItemSelected(R.id.bottom_home, true);


//
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment1, new home_fragment()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment2, new category_fragment()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment3, new Saved_fragment()).commit();
//        getSupportFragmentManager().beginTransaction().replace(R.id.fragment4, new profile_()).commit();

//        if (savedInstanceState == null) {
//            home_fragmentt = home_fragment
//            category_fragmentt = category_fragment.newInstance("bar");
//            saved_fragmentt = Saved_fragment.newInstance("baz");
//            profile_t = profile_.newInstance("baz");
//        }

//        ft = getFragmentManager().beginTransaction();
//        ft.setCustomAnimations(android.R.animator.fade_in, android.R.animator.fade_out);

        View f1 = findViewById(R.id.fragment1);
        View f2 = findViewById(R.id.fragment2);
        View f3 = findViewById(R.id.fragment3);
        View f4 = findViewById(R.id.fragment4);

        f1.setVisibility(View.VISIBLE);
        f2.setVisibility(View.INVISIBLE);
        f3.setVisibility(View.INVISIBLE);
        f4.setVisibility(View.INVISIBLE);

//        getSupportFragmentManager().beginTransaction().replace(R.id.main_home_screen, new home_fragment()).commit();

        menu_bar();
    }

    public void menu_bar() {
        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                View f1 = findViewById(R.id.fragment1);
                View f2 = findViewById(R.id.fragment2);
                View f3 = findViewById(R.id.fragment3);
                View f4 = findViewById(R.id.fragment4);
                Fragment fragment = null;
                switch (i) {
                    case R.id.bottom_home:
                        f1.setVisibility(View.VISIBLE);
                        f2.setVisibility(View.INVISIBLE);
                        f3.setVisibility(View.INVISIBLE);
                        f4.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.bottom_categories:
                        f1.setVisibility(View.INVISIBLE);
                        f2.setVisibility(View.VISIBLE);
                        f3.setVisibility(View.INVISIBLE);
                        f4.setVisibility(View.INVISIBLE);
                        break;
                    case R.id.bottom_saved:
                        f1.setVisibility(View.INVISIBLE);
                        f2.setVisibility(View.INVISIBLE);
                        f3.setVisibility(View.VISIBLE);
                        f4.setVisibility(View.INVISIBLE);
//                        Saved_fragment
                        break;
                    case R.id.bottom_profile:
                        f1.setVisibility(View.INVISIBLE);
                        f2.setVisibility(View.INVISIBLE);
                        f3.setVisibility(View.INVISIBLE);
                        f4.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });
    }






}