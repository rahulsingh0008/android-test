package com.example.campusnavigation

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import org.osmdroid.config.Configuration
import org.osmdroid.tileprovider.tilesource.TileSourceFactory
import org.osmdroid.util.GeoPoint
import org.osmdroid.views.MapView
import org.osmdroid.views.overlay.Marker
import org.osmdroid.views.overlay.Polyline

class MainActivity : AppCompatActivity() {

    lateinit var map: MapView
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationCallback: LocationCallback
    private lateinit var startSpinner: Spinner
    private lateinit var endSpinner: Spinner
    private lateinit var drawButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Load OpenStreetMap settings
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))

        setContentView(R.layout.activity_maps)

        startSpinner = findViewById(R.id.startLocationSpinner)
        endSpinner = findViewById(R.id.endLocationSpinner)
        drawButton = findViewById(R.id.drawPathButton)

        val locationNames = CampusLocations.locations.keys.toList()

        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, locationNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        startSpinner.adapter = adapter
        endSpinner.adapter = adapter

        // Initialize the map view
        map = findViewById(R.id.mapView)

        // Set the map to use OpenStreetMap tiles
        map.setTileSource(TileSourceFactory.MAPNIK)

        // Enable multi-touch controls (zoom, pan)
        map.setMultiTouchControls(true)

        // Set initial location (replace with your campus coordinates)
        val startPoint = GeoPoint(30.2675, 77.9960) // Replace with actual latitude & longitude of your campus
        map.controller.setZoom(18.0)
        map.controller.setCenter(startPoint)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

//        getCurrentLocation()

        // Loop through the campus locations and add markers dynamically
        for ((title, location) in CampusLocations.locations) {
            addMarker(location, title)
        }

        drawButton.setOnClickListener {
            val start = startSpinner.selectedItem.toString()
            val end = endSpinner.selectedItem.toString()

            val (shortestPath, _) = Dijkstra.shortestPath(CampusGraph.edges, start, end)
            drawPath(shortestPath)
        }
    }

    private fun getCurrentLocation() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION), 1)
            return
        }

        val locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000)
            .setWaitForAccurateLocation(false)
            .setMinUpdateIntervalMillis(5000)
            .setMaxUpdateDelayMillis(10000)
            .setMaxUpdates(1)
            .build()

        locationCallback = object : LocationCallback() {
            override fun onLocationResult(result: LocationResult) {
                val location = result.lastLocation
                if (location != null) {
                    val currentPoint = GeoPoint(location.latitude, location.longitude)
                    addMarker(currentPoint, "You are here")
                    map.controller.setCenter(currentPoint)
                }
            }
        }

        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
    }

    private fun addMarker(location: GeoPoint, title: String) {
        val marker = Marker(map)
        marker.position = location
        marker.setAnchor(Marker.ANCHOR_CENTER, Marker.ANCHOR_BOTTOM)
        marker.title = title
        map.overlays.add(marker)
    }

    private fun drawPath(path: List<String>) {
        // Clear previous overlays except markers
        map.overlays.removeAll { it is Polyline }

        val polyline = Polyline()
        polyline.title = "Shortest Path"

        val geoPoints = path.mapNotNull { locationName ->
            CampusLocations.locations[locationName]
        }

        polyline.setPoints(geoPoints)
        map.overlays.add(polyline)
        map.invalidate()
    }
}
