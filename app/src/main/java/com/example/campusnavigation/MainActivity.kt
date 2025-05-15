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

    // for displaying OpenStreetMap
    lateinit var map: MapView
    // For accessing current location using Google Location Services
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    // To react to location updates (currently not used)
    private lateinit var locationCallback: LocationCallback
    // Dropdowns for selecting starting and ending campus locations
    private lateinit var startSpinner: Spinner
    private lateinit var endSpinner: Spinner
    // button to draw the path
    private lateinit var drawButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Loads the default map settings
        Configuration.getInstance().load(applicationContext, androidx.preference.PreferenceManager.getDefaultSharedPreferences(applicationContext))

        // for screen UI
        setContentView(R.layout.activity_maps)

        startSpinner = findViewById(R.id.startLocationSpinner)
        endSpinner = findViewById(R.id.endLocationSpinner)
        drawButton = findViewById(R.id.drawPathButton)

        // list containing all the campus location names
        val locationNames = CampusLocations.locations.keys.toList()

        // Creates an adapter to show the location names in dropdowns
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, locationNames)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        startSpinner.adapter = adapter
        endSpinner.adapter = adapter

        // Initialize the map view
        map = findViewById(R.id.mapView)

        // Set the map to use OpenStreetMap tiles
        map.setTileSource(TileSourceFactory.MAPNIK)

        // Enables pinch-zoom and drag with fingers
        map.setMultiTouchControls(true)

        val startPoint = GeoPoint(30.2675, 77.9960) // Replace with actual latitude & longitude of your campus
        map.controller.setZoom(18.0)
        map.controller.setCenter(startPoint)

        // Prepares to use the user’s current GPS location (currently not in use)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        // getCurrentLocation()

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
