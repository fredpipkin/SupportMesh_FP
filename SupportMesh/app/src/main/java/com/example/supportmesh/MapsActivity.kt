package com.example.supportmesh

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.supportmesh.databinding.ActivityMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private var username: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        username = intent.getStringExtra("USERNAME").toString()

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // If the list button is clicked, navigate to the list screen
        val listViewButton = findViewById<Button>(R.id.listViewButton);
        listViewButton.setOnClickListener {
            val intent = Intent(this, RequestActivity::class.java);
            intent.putExtra("USERNAME", username);
            startActivity(intent);
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Load existing requests from the singleton
        if (SharedRequests.requests.isEmpty()) {
            SharedRequests.loadRequests()
        }

        // Add markers on the map for the existing requests
        for(req in SharedRequests.requests) {
            val pos = LatLng(req.lat, req.long)
            mMap.addMarker(MarkerOptions().position(pos).title(req.title).snippet(req.description))
        }

        // Add a marker for Gardens House as default location and zoom the camera here
        val gardens = LatLng(51.47476, -0.081)
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(gardens, 15f))

        mMap.setOnMapClickListener { latLng ->
            // Show dialog for adding a new request when somewhere on the map is clicked
            showAddMarkerDialog(latLng)
        }

    }

    private fun showAddMarkerDialog(latLng: LatLng) {
        val builder = AlertDialog.Builder(this)
        val view = layoutInflater.inflate(R.layout.add_marker_dialog, null)
        val titleInput = view.findViewById<EditText>(R.id.markerTitle)
        val descriptionInput = view.findViewById<EditText>(R.id.markerDescription)

        builder.setView(view)
            .setTitle("Add Request")
            .setPositiveButton("Add") { _, _ ->
                val title = titleInput.text.toString()
                val description = descriptionInput.text.toString()
                addMarker(latLng, title, description)
            }
            .setNegativeButton("Cancel", null)
            .show()
    }

    private fun addMarker(latLng: LatLng, title: String, description: String) {
        mMap.addMarker(MarkerOptions().position(latLng).title(title).snippet(description))

        SharedRequests.saveRequest(
            RequestItem(
                user = username.toString(),
                lat = latLng.latitude,
                long = latLng.longitude,
                title = title,
                description = description
            ))
    }
}
