package com.example.flexfilmes;

// Imports
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

// Activity Minha Lista
public class MyListActivity extends AppCompatActivity {

    // Inicialização
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist);

        // ActionBar
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // RecyclerView
        RecyclerView recyclerView = findViewById(R.id.recycler_mylist);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));

        // Dados
        List<Movie> myList = CatalogActivity.getMyListMovies();

        // Adapter
        MovieAdapter adapter = new MovieAdapter(this, myList);
        recyclerView.setAdapter(adapter);
    }

    // Botão voltar
    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}