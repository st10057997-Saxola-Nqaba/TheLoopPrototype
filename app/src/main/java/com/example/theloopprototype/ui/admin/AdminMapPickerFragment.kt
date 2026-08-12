package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.theloopprototype.R
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback

class AdminMapPickerFragment : Fragment(R.layout.fragment_admin_map_picker), OnMapReadyCallback {

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Initialize the MapView from the layout
        mapView = view.findViewById(R.id.mapViewCluster)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)
    }

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        // Configure your map here (e.g., move camera, add markers)
    }

    override fun onResume() {
        super.onResume()
        mapView?.onResume()
    }

    override fun onPause() {
        mapView?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView?.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView?.onLowMemory()
    }
}