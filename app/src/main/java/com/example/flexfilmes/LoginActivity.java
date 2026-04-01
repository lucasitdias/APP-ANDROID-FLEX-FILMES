package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private EditText inputEmail, inputPassword;
    private Button btnLogin;
    private CheckBox rememberMe;
    private TextView helpText, signupText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Referências aos campos
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnLogin = findViewById(R.id.btn_login);
        rememberMe = findViewById(R.id.remember_me);
        helpText = findViewById(R.id.help_text);
        signupText = findViewById(R.id.signup_text);

        // Ação do botão de login
        btnLogin.setOnClickListener(v -> {
            String email = inputEmail.getText().toString();
            String password = inputPassword.getText().toString();
            boolean remember = rememberMe.isChecked();

            if (!email.isEmpty() && !password.isEmpty()) {
                // TODO: lógica de autenticação real
                Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                // Exemplo: vai para a tela principal
                startActivity(new Intent(LoginActivity.this, CatalogActivity.class));
                finish();
            } else {
                Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
            }
        });

        // Ação do link "Precisa de ajuda?"
        helpText.setOnClickListener(v -> {
            // Exemplo: abrir tela de ajuda ou FAQ
            Toast.makeText(this, "Abrir tela de ajuda...", Toast.LENGTH_SHORT).show();
        });

        // Ação do link "Novo por aqui? Assine agora."
        signupText.setOnClickListener(v -> {
            // Exemplo: abrir tela de cadastro
            startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
        });
    }
}
