package com.example.flexfilmes;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

// Seção: Activity de Cadastro
public class SignUpActivity extends BaseActivity {

    // Seção: views
    private EditText inputName, inputEmail, inputPassword;
    private Button btnSignUp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        // Seção: toolbar
        setupToolbarCustom(true, false);

        // Seção: bind das views
        inputName = findViewById(R.id.input_name);
        inputEmail = findViewById(R.id.input_email);
        inputPassword = findViewById(R.id.input_password);
        btnSignUp = findViewById(R.id.btn_signup);

        // Seção: botão de cadastro
        if (btnSignUp != null) {
            btnSignUp.setOnClickListener(v -> {
                String name = (inputName != null) ? inputName.getText().toString().trim() : "";
                String email = (inputEmail != null) ? inputEmail.getText().toString().trim() : "";
                String password = (inputPassword != null) ? inputPassword.getText().toString() : "";

                // Seção: validação de campos
                if (name.isEmpty()) {
                    if (inputName != null) inputName.setError(getString(R.string.error_name_required));
                    return;
                }
                if (email.isEmpty()) {
                    if (inputEmail != null) inputEmail.setError(getString(R.string.error_email_required));
                    return;
                }
                if (password.isEmpty()) {
                    if (inputPassword != null) inputPassword.setError(getString(R.string.error_password_required));
                    return;
                }

                // Seção: salvar dados no SharedPreferences
                SharedPreferences prefs = getSharedPreferences("flex_prefs", MODE_PRIVATE);
                prefs.edit()
                        .putBoolean("logged_in", true)
                        .putString("user_name", name)
                        .putString("user_email", email)
                        .apply();

                // Seção: feedback
                Toast.makeText(SignUpActivity.this, "Cadastro realizado com sucesso!", Toast.LENGTH_SHORT).show();

                // Seção: navegação para WelcomeActivity
                Intent intent = new Intent(SignUpActivity.this, WelcomeActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            });
        }
    }
}