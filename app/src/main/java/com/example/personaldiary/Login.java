package com.example.personaldiary;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import org.mindrot.jbcrypt.BCrypt;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        Toast.makeText(getApplicationContext(),"Welcome!!!" , Toast.LENGTH_SHORT).show();

    }

    public void Login(View v) {

        EditText PasswordEditText = findViewById(R.id.passwordEditText);
        String newEnteredPassword = PasswordEditText.getText().toString().trim();

        if (!newEnteredPassword.isEmpty()) {
            SharedPreferences sharedPreferences = getSharedPreferences("PREF_NAME", Context.MODE_PRIVATE);
            String hashpassword = sharedPreferences.getString("hashpassword", null);
            if (hashpassword != null) {
                if (BCrypt.checkpw(newEnteredPassword, hashpassword)) {
                    // Password matches
                    Toast.makeText(getApplicationContext(), "Successfully Logged In!",Toast.LENGTH_SHORT).show();
                    // Perform password change or other actions here
                    Intent intent = new Intent( Login.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    // Password does not match
                    Toast.makeText(getApplicationContext(), "Invalid password!", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Password not Found!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}