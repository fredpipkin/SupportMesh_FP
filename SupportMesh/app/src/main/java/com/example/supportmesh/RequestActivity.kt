package com.example.supportmesh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RequestActivity : AppCompatActivity() {

    private lateinit var adapter: RequestAdapter
    private lateinit var allRequests: List<RequestItem>
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.request_layout)

        // Load the existing requests from the singleton
        if (SharedRequests.requests.isEmpty()) {
            SharedRequests.loadRequests()
        }

        // Grab the username from the intent
        username = intent.getStringExtra("USERNAME").toString()

        // If the map button is clicked, navigate to the map screen
        val mapViewButton = findViewById<Button>(R.id.mapViewButton);
        mapViewButton.setOnClickListener {
            val intent = Intent(this, MapsActivity::class.java);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        }

        // Load all requests from your singleton
        allRequests = SharedRequests.requests

        // Set up RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.requestsRecyclerView)
        adapter = RequestAdapter(allRequests)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        // Set up filtering checkbox to filter requests for current user
        val checkBox = findViewById<CheckBox>(R.id.checkbox_my_requests)
        checkBox.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val filtered = allRequests.filter { it.user == username }
                adapter.updateList(filtered)
            }
            else {
                adapter.updateList(allRequests)
            }
        }
    }
}