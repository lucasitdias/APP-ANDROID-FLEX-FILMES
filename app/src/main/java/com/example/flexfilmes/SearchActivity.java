package com.example.flexfilmes;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

// Seção: Activity de Busca
public class SearchActivity extends BaseActivity {

    // Seção: views
    private SearchView searchView;
    private RecyclerView recyclerResults;

    // Seção: adapter e dados
    private MovieAdapter resultsAdapter;
    private List<Movie> resultsList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        // Seção: toolbar
        setupToolbarCustom(true, false);

        // Seção: bind das views
        searchView = findViewById(R.id.search_view);
        recyclerResults = findViewById(R.id.recycler_search_results);

        // Seção: preparar RecyclerView
        resultsList = new ArrayList<>();
        resultsAdapter = new MovieAdapter(this, resultsList);
        if (recyclerResults != null) {
            recyclerResults.setLayoutManager(new LinearLayoutManager(this));
            recyclerResults.setAdapter(resultsAdapter);
        }

        // Seção: configurar SearchView
        if (searchView != null) {
            searchView.setIconifiedByDefault(false);
            searchView.setQueryHint(getString(R.string.search_hint));

            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    performSearch(query);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return true;
                }
            });
        }
    }

    // Seção: busca de filmes
    @Override
    protected void performSearch(String rawQuery) {
        if (rawQuery == null) rawQuery = "";
        String query = rawQuery.trim().toLowerCase();

        List<Movie> source = new ArrayList<>();
        source.addAll(CatalogActivity.getContinueWatchingMovies());
        source.addAll(CatalogActivity.getMyListMovies());
        source.addAll(CatalogActivity.getTopPicksMovies());
        source.addAll(CatalogActivity.getRecentMovies());

        List<Movie> filtered = new ArrayList<>();
        for (Movie m : source) {
            if (m == null) continue;
            if (m.getTitle() != null && m.getTitle().toLowerCase().contains(query)) {
                filtered.add(m);
            }
        }

        Log.d("SEARCH_ACTIVITY", "performSearch query='" + query + "' results=" + filtered.size());

        resultsList.clear();
        resultsList.addAll(filtered);
        if (resultsAdapter != null) {
            resultsAdapter.notifyDataSetChanged();
        }
    }
}