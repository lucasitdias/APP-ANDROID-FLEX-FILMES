package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.navigation.NavigationView;

import java.util.List;

// Seção: Activity Minha Lista
public class MyListActivity extends BaseActivity {

    // Seção: RecyclerView
    private RecyclerView recyclerMyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        // Seção: drawer e navigation
        drawerLayout = findViewById(R.id.drawerLayout);
        NavigationView navigationView = findViewById(R.id.navigationView);

        // Seção: toolbar customizada
        setupToolbarCustom(true, true);

        // Seção: RecyclerView Minha Lista
        recyclerMyList = findViewById(R.id.recycler_mylist);
        if (recyclerMyList != null) {
            recyclerMyList.setLayoutManager(
                    new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
            );

            List<Movie> myList = CatalogActivity.getMyListMovies(); // Seção: lista de filmes do usuário
            MovieAdapter adapter = new MovieAdapter(this, myList);
            recyclerMyList.setAdapter(adapter);
        }

        // Seção: menu lateral
        if (navigationView != null && drawerLayout != null) {
            navigationView.setNavigationItemSelectedListener(item -> {
                int id = item.getItemId();
                if (id == R.id.nav_home) {
                    startActivity(new Intent(this, MainActivity.class));
                } else if (id == R.id.nav_profile) {
                    startActivity(new Intent(this, SignUpActivity.class));
                } else if (id == R.id.nav_mylist) {
                    // Seção: rolar lista para o início caso já esteja na tela
                    if (recyclerMyList != null) recyclerMyList.scrollToPosition(0);
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            });
        }
    }
}