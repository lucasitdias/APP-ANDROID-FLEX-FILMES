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

    // Componentes
    private EditText inputEmail, inputPassword;
    private CheckBox rememberMe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Bind das views (pode retornar null se o id não existir no layout)
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        Button btnLogin = findViewById(R.id.btn_login);
        rememberMe = findViewById(R.id.remember_me);
        TextView helpText = findViewById(R.id.help_text);
        TextView signupText = findViewById(R.id.signup_text);

        // Segurança: checar null antes de usar
        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                try {
                    String email = (inputEmail != null) ? inputEmail.getText().toString().trim() : "";
                    String password = (inputPassword != null) ? inputPassword.getText().toString() : "";
                    boolean remember = (rememberMe != null) && rememberMe.isChecked();

                    if (!email.isEmpty() && !password.isEmpty()) {
                        Toast.makeText(this, "Login realizado com sucesso!", Toast.LENGTH_SHORT).show();

                        // Navegação para catálogo
                        Intent intent = new Intent(LoginActivity.this, CatalogActivity.class);
                        // Se quiser passar extras, valide antes de colocar:
                        // intent.putExtra("userEmail", email);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    // Evita crash e registrar o erro
                    Log.e(TAG, "Erro no clique de login", e);
                    Toast.makeText(LoginActivity.this, "Erro ao processar login. Veja o log.", Toast.LENGTH_LONG).show();
                }
            });
        } else {
            Log.w(TAG, "btn_login não encontrado no layout (findViewById retornou null). Verifique activity_login.xml");
        }

        if (helpText != null) {
            helpText.setOnClickListener(v -> Toast.makeText(this, "Abrir tela de ajuda...", Toast.LENGTH_SHORT).show());
        } else {
            Log.w(TAG, "help_text não encontrado no layout");
        }

        if (signupText != null) {
            signupText.setOnClickListener(v -> {
                try {
                    startActivity(new Intent(LoginActivity.this, SignUpActivity.class));
                } catch (Exception e) {
                    Log.e(TAG, "Erro ao abrir SignUpActivity", e);
                    Toast.makeText(LoginActivity.this, "Erro ao abrir cadastro.", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Log.w(TAG, "signup_text não encontrado no layout");
        }
    }
}
