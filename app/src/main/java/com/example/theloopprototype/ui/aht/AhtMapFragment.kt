package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.theloopprototype.R
import com.example.theloopprototype.DummyData
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AhtMapFragment : Fragment(R.layout.fragment_aht_map), OnMapReadyCallback {

    private var googleMapInstance: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        googleMapInstance = googleMap

        // Default location context (Centurion area as fallback)
        var centerLocation = LatLng(-25.8581, 28.1859)

        // Pull actual requests from DummyData and place markers
        val requests = DummyData.requests ?: emptyList()
        var markerCount = 0

        for (request in requests) {
            val lat = request.latitude
            val lng = request.longitude

            if (lat != null && lng != null) {
                val position = LatLng(lat, lng)

                // Add a pin for each request
                googleMap.addMarker(
                    MarkerOptions()
                        .position(position)
                        .title("Request: ${request.id}")
                        .snippet("Severity: ${request.severity} | Status: ${request.status}")
                )

                // Set the first valid request location as our camera focus point
                if (markerCount == 0) {
                    centerLocation = position
                }
                markerCount++
            }
        }

        // Move camera to center around the requests with a useful zoom level
        googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(centerLocation, 13f))
    }
}