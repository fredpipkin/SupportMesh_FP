package com.example.supportmesh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity

class ForgotActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.forgot_layout);
        val resetButton = findViewById<Button>(R.id.resetButton);
        resetButton.setOnClickListener {
            // Show a message and navigate back to the login screen. This can be updated to a full reset system.
            Toast.makeText(this, "Check your email for reset instructions.", Toast.LENGTH_SHORT).show();
            val intent = Intent(this, MainActivity::class.java);
            startActivity(intent);
        }
    }
}
