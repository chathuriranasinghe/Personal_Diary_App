package com.example.personaldiary;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import org.mindrot.jbcrypt.BCrypt;

public class Settings extends AppCompatActivity {

    public EditText textName;
    TextView textView;
    public SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_settings);
        textView = findViewById(R.id.textView4);
        textName =findViewById(R.id.editPersonName);
        sharedPreferences = getSharedPreferences("MySharedPref",MODE_PRIVATE);
        // Load the username if it exists
        String savedUsername = sharedPreferences.getString("username", "");

}

    private void saveUsername(String username) {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("username", username);
        editor.apply();

    }

    public void saveUser(View view) {
        String username = textName.getText().toString().trim();
        if (!username.isEmpty()) {
            saveUsername(username);
            Toast.makeText(getApplicationContext(), "Username Changed Successfully!", Toast.LENGTH_SHORT).show();
            finish();
        }
    }

    public void savePassword(View view) {
        EditText passwordEditText;
        passwordEditText = findViewById(R.id.editPassword);
        String password = passwordEditText.getText().toString().trim();
        if (password.isEmpty()) {
            Toast.makeText(this, "Please enter a password", Toast.LENGTH_SHORT).show();
            return;
        }
        if (!password.isEmpty()) {
// Hash the password using bcrypt
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
            saveHashedPassword(hashedPassword);

            Toast.makeText(Settings.this, "Password Changed Successfully!", Toast.LENGTH_SHORT).show();

        }}

    private void saveHashedPassword(String hashedPassword) {
        SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("hashpassword", hashedPassword);
        editor.apply();
    }

}