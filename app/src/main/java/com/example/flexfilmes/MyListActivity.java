package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

/**
 * Seção: Activity Minha Lista
 *
 * Exibe os filmes salvos pelo usuário e reutiliza a toolbar padronizada.
 * Comentários padronizados: // Seção: descrição
 */
public class MyListActivity extends AppCompatActivity {

    // Seção: views
    private RecyclerView recyclerMyList;

    // Seção: ciclo de vida
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mylist); // ajuste se o layout tiver outro nome

        // Seção: configurar toolbar padronizada (sem seta de voltar nesta tela)
        setupToolbar(false);

        // Seção: RecyclerView de "Minha Lista"
        recyclerMyList = findViewById(R.id.recycler_mylist);
        if (recyclerMyList != null) {
            recyclerMyList.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            List<Movie> myList = CatalogActivity.getMyListMovies();
            MovieAdapter adapter = new MovieAdapter(this, myList);
            recyclerMyList.setAdapter(adapter);
        }
    }

    /**
     * Seção: Método padronizado para configurar toolbar
     *
     * @param showBackArrow se true, mostra ícone de voltar; se false, remove ícone de navegação
     */
    private void setupToolbar(boolean showBackArrow) {
        Toolbar toolbar = findViewById(R.id.toolbar_flexfilmes);
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar);
        }
        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        // Seção: evita título duplicado (desabilita o título padrão da ActionBar)
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // Seção: logo e título central clicáveis (levam para a home/catalog)
        View logo = toolbar.findViewById(R.id.toolbar_logo);
        View title = toolbar.findViewById(R.id.toolbar_title);
        View.OnClickListener goHome = v -> {
            Intent i = new Intent(this, CatalogActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        };
        if (logo != null) logo.setOnClickListener(goHome);
        if (title != null) title.setOnClickListener(goHome);

        // Seção: seta de voltar (quando solicitado)
        if (showBackArrow) {
            toolbar.setNavigationIcon(R.drawable.icone_voltar);
            toolbar.setNavigationContentDescription("Voltar");
            toolbar.setNavigationOnClickListener(v -> {
                Intent intent = new Intent(this, CatalogActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
                startActivity(intent);
                finish();
            });
        } else {
            toolbar.setNavigationIcon(null);
        }

        // Seção: overflow custom (ImageView) abre o menu de overflow — lookup seguro
        int overflowId = getResources().getIdentifier("toolbar_overflow", "id", getPackageName());
        View overflow = (overflowId != 0) ? toolbar.findViewById(overflowId) : null;
        if (overflow != null) {
            final Toolbar toolbarFinal = toolbar;
            overflow.setOnClickListener(v -> toolbarFinal.showOverflowMenu());
        }

        // Seção: tratar clique do ícone de busca (lookup seguro)
        int searchId = getResources().getIdentifier("toolbar_search", "id", getPackageName());
        View searchIcon = (searchId != 0) ? toolbar.findViewById(searchId) : null;
        if (searchIcon != null) {
            searchIcon.setOnClickListener(v -> {
                Intent intent = new Intent(this, CatalogActivity.class);
                intent.putExtra("open_search", true);
                startActivity(intent);
            });
        }

        // Seção: listener único do menu (corrige duplicações anteriores)
        toolbar.setOnMenuItemClickListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.menu_profile) {
                startActivity(new Intent(this, SignUpActivity.class));
                return true;
            } else if (itemId == R.id.menu_catalog) {
                startActivity(new Intent(this, CatalogActivity.class));
                return true;
            } else if (itemId == R.id.menu_home) {
                startActivity(new Intent(this, MainActivity.class));
                return true;
            } else if (itemId == R.id.menu_mylist) {
                if (recyclerMyList != null) recyclerMyList.scrollToPosition(0);
                return true;
            } else if (itemId == R.id.menu_settings) {
                Toast.makeText(this, "Configurações", Toast.LENGTH_SHORT).show();
                return true;
            }
            return false;
        });
    }
}
