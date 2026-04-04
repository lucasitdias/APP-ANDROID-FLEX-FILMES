package com.example.flexfilmes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;

// Seção: Activity de boas-vindas
public class WelcomeActivity extends BaseActivity {

    // Seção: views
    private TextView txtWelcomeName;
    private Button btnOpenCatalog;
    private Button btnMyList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        // Seção: validação de login
        SharedPreferences prefs = getSharedPreferences("flex_prefs", MODE_PRIVATE);
        boolean loggedIn = prefs.getBoolean("logged_in", false);
        if (!loggedIn) {
            Intent i = new Intent(WelcomeActivity.this, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(i);
            finish();
            return;
        }

        // Seção: bind das views
        txtWelcomeName = findViewById(R.id.txtWelcomeName);
        btnOpenCatalog = findViewById(R.id.btnOpenCatalog);
        btnMyList = findViewById(R.id.btnMyList);

        // Seção: preencher mensagem de boas-vindas
        String name = prefs.getString("user_name", "");
        if (txtWelcomeName != null) {
            if (!name.isEmpty()) {
                txtWelcomeName.setText(getString(R.string.welcome_user_format, name));
            } else {
                txtWelcomeName.setText(getString(R.string.welcome_message));
            }
        }

        // Seção: botões de navegação
        if (btnOpenCatalog != null) {
            btnOpenCatalog.setOnClickListener(v -> {
                Intent i = new Intent(WelcomeActivity.this, CatalogActivity.class);
                startActivity(i);
            });
        }

        if (btnMyList != null) {
            btnMyList.setOnClickListener(v -> {
                Intent i = new Intent(WelcomeActivity.this, MyListActivity.class);
                startActivity(i);
            });
        }
    }
}