package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        // configura toolbar padronizada (sem seta de voltar)
        setupToolbar(false);

        // Botões de navegação rápida (se existirem no layout)
        Button btnCatalog = findViewById(R.id.btnOpenCatalog);
        Button btnMyList = findViewById(R.id.btnOpenMyList);

        if (btnCatalog != null) {
            btnCatalog.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, CatalogActivity.class);
                startActivity(i);
            });
        }

        if (btnMyList != null) {
            btnMyList.setOnClickListener(v -> {
                Intent i = new Intent(MainActivity.this, MyListActivity.class);
                startActivity(i);
            });
        }
    }

    // Método padronizado para configurar toolbar (mesma implementação usada nas outras Activities)
    private void setupToolbar(boolean showBackArrow) {
        // tenta obter o include padrão
        Toolbar toolbar = findViewById(R.id.toolbar_flexfilmes);
        if (toolbar == null) {
            toolbar = findViewById(R.id.toolbar); // fallback
        }
        if (toolbar == null) return;

        setSupportActionBar(toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowTitleEnabled(false);
        }

        // logo e título central clicáveis (levam para a home/catalog)
        View logo = toolbar.findViewById(R.id.toolbar_logo);
        View title = toolbar.findViewById(R.id.toolbar_title);
        View.OnClickListener goHome = v -> {
            Intent i = new Intent(this, CatalogActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
        };
        if (logo != null) logo.setOnClickListener(goHome);
        if (title != null) title.setOnClickListener(goHome);

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

        // overflow custom (ImageView) abre o menu de overflow — verifica null para evitar erro de compilação
        int overflowId = getResources().getIdentifier("toolbar_overflow", "id", getPackageName());
        View overflow = (overflowId != 0) ? toolbar.findViewById(overflowId) : null;
        if (overflow != null) {
            final Toolbar toolbarFinal = toolbar;
            overflow.setOnClickListener(v -> toolbarFinal.showOverflowMenu());
        }

        // tratar cliques do menu (overflow) via listener do toolbar
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
                startActivity(new Intent(this, MyListActivity.class));
                return true;
            } else if (itemId == R.id.menu_settings) {
                // abrir configurações (implemente se houver Activity)
                return true;
            } else if (itemId == R.id.action_more) {
                // ação extra
                return true;
            }
            return false;
        });
    }
}
