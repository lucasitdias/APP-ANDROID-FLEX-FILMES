package com.example.flexfilmes;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";

    // Campos de entrada
    private EditText inputEmail, inputPassword;

    // Checkbox "lembrar usuário"
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Mapeia views do layout
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        Button btnLogin = findViewById(R.id.btn_login);
        rememberMe = findViewById(R.id.remember_me);
        TextView helpText = findViewById(R.id.help_text);
        TextView signupText = findViewById(R.id.signup_text);

        // Botão login
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                try {

                    // Captura dados digitados
                    String email = (inputEmail != null) ? inputEmail.getText().toString().trim() : "";
                    String password = (inputPassword != null) ? inputPassword.getText().toString() : "";
                    boolean remember = (rememberMe != null) && rememberMe.isChecked();

                    // Valida campos
                    if (!email.isEmpty() && !password.isEmpty()) {

                        // Login OK
                        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        // Abre tela de catálogo
                        Intent intent = new Intent(LoginActivity.this, CatalogActivity.class);

                        // Exemplo de envio de dados
                        // intent.putExtra("userEmail", email);

                        startActivity(intent);
                        finish();

                    } else {
                        // Campos vazios
                        Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {

                    // Evita crash e registra erro
                    Log.e(TAG, "Erro no clique de login", e);

                    Toast.makeText(
                            LoginActivity.this,
                            "Erro ao processar login. Veja o log.",
                            Toast.LENGTH_LONG
                    ).show();
                }
            });
        } else {
            // Botão não encontrado no layout
            Log.w(TAG, "btn_login não encontrado no layout (findViewById retornou null). Verifique activity_login.xml");
        }

        // Texto de ajuda
        if (helpText != null) {
            helpText.setOnClickListener(v ->
                    Toast.makeText(this, "Abrir tela de ajuda...", Toast.LENGTH_SHORT).show()
            );
        } else {
            // View não encontrada
            Log.w(TAG, "help_text não encontrado no layout");
        }

        // Texto de cadastro
        if (signupText != null) {
            signupText.setOnClickListener(v -> {
                try {
                    // Abre tela de cadastro
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                } catch (Exception e) {

                    // Erro ao abrir tela
                    Log.e(TAG, "Erro ao abrir SignUpActivity", e);

                    Toast.makeText(
                            LoginActivity.this,
                            "Erro ao abrir cadastro.",
                            Toast.LENGTH_SHORT
                    ).show();
                }
            });
        } else {
            // View não encontrada
            Log.w(TAG, "signup_text não encontrado no layout");
        }
    }
}