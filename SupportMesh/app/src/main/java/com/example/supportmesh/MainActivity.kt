package com.example.supportmesh

import android.os.Bundle
import androidx.activity.ComponentActivity
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import java.security.MessageDigest;
import android.widget.Toast;

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_layout);

        // Forward the user to the forgot password screen
        val forgotPasswordButton = findViewById<Button>(R.id.forgotButton);
        forgotPasswordButton.setOnClickListener {
            val intent = Intent(this, ForgotActivity::class.java);
            startActivity(intent);
        }

        // Grab the inputs from the screen
        val loginButton = findViewById<Button>(R.id.loginButton);
        val username = findViewById<EditText>(R.id.username);
        val password = findViewById<EditText>(R.id.password);

        loginButton.setOnClickListener {
            // When the login button is clicked validate the user
            if (validatePassword(username.text.toString(), password.text.toString())) {
                val intent = Intent(this, MapsActivity::class.java);
                intent.putExtra("USERNAME", username.text.toString());
                startActivity(intent);
            }
            else {
                Toast.makeText(this, "Invalid username or password", Toast.LENGTH_SHORT).show();
            }
        }
    }

    fun validatePassword(username: String, password: String): Boolean {
        // Hash the password using SHA-256 for security
        val bytes = password.toByteArray();
        val md = MessageDigest.getInstance("SHA-256");
        val digest = md.digest(bytes);
        val hashedPassword = digest.joinToString("") { "%02x".format(it) };

        // Check if the username and hashed password match the admin account. This can be changed to check against a central user db.
        if (username == "admin" && hashedPassword == "d74ff0ee8da3b9806b18c877dbf29bbde50b5bd8e4dad7a3a725000feb82e8f1")
            return true;
        else
            return false;
    }
}
