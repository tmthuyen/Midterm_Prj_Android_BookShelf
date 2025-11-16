package com.example.myapplication;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.myapplication.ui.fragments.HomeFragment;
import com.example.myapplication.ui.fragments.LibraryFragment;
import com.example.myapplication.ui.fragments.SearchFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);
        if (bottomNav != null) {
            bottomNav.setOnItemSelectedListener(this::onNavigationItemSelected);
        }
        if (savedInstanceState == null) {
            loadFragment(new HomeFragment());
        }
    }
    private boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment fragment;
        int id = item.getItemId();
        if (id == R.id.navigation_home) {
            fragment = new HomeFragment();
        } else if (id == R.id.navigation_search) {
            fragment = new SearchFragment();
        } else if (id == R.id.navigation_library) {
            fragment = new LibraryFragment();
        } else {
            return false;
        }
        loadFragment(fragment);
        return true;
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment);
        transaction.commit();
    }

}