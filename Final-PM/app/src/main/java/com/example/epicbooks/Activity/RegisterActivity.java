package com.example.epicbooks.Activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.epicbooks.R;

public class RegisterActivity extends AppCompatActivity {

    EditText et_username, et_password;
    Button btn_register;
    SharedPreferences sharedPreferences;

    private void saveUser(String username, String password) {
        sharedPreferences = getSharedPreferences("user_pref", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        // Simpan akun pengguna dalam SharedPreferences
        editor.putString("username", username);
        editor.putString("password", password);
        editor.putBoolean("isLoggedIn", true);
        editor.apply();

        // Menampilkan Toast di dalam thread UI utama
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                Toast.makeText(RegisterActivity.this, "Registration successful", Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        et_username = findViewById(R.id.username);
        et_password = findViewById(R.id.password);
        btn_register = findViewById(R.id.btn_register);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = et_username.getText().toString().trim();
                String password = et_password.getText().toString().trim();

                if (!username.isEmpty() && !password.isEmpty()) {
                    // Membuat thread baru untuk menjalankan proses registrasi
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            
                            try {
                                Thread.sleep(2000); // Simulasi proses registrasi dengan tidur selama 2 detik
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            // Panggil metode untuk menyimpan pengguna setelah proses tidur selesai
                            saveUser(username, password);
                        }
                    }).start();
                } else {
                    Toast.makeText(RegisterActivity.this, "Please enter Username and password", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
