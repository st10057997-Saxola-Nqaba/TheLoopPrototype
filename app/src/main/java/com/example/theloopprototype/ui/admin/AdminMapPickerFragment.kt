package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.RequestStatus
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.time.format.DateTimeFormatter

class AdminMapPickerFragment : Fragment(R.layout.fragment_admin_map_picker), OnMapReadyCallback {

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapViewCluster)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        val btnConfirm = view.findViewById<Button>(R.id.btnConfirmPinLocation)
        btnConfirm?.setOnClickListener {
            Toast.makeText(requireContext(), "Schedule location confirmed & list created!", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        }
    }


    // Code Attribution
// Fix for map pin drop not resolving a real area
// Used nearest-neighbour pattern to resolve areaId by comparing tapped
// LatLng against pending request coordinates
// Automating GIS Processes (2018) Nearest Neighbour Analysis.
// https://automating-gis-processes.github.io/CSC18/lessons/L4/nearest-neighbour.html

    override fun onMapReady(map: GoogleMap) {
        googleMap = map
        val tembisa = LatLng(-25.9987, 28.2201)
        map.moveCamera(CameraUpdateFactory.newLatLngZoom(tembisa, 12f))

        // Plot pending request pins containing timestamps derived from createdAt
        DummyRequests.requests.filter { it.latitude != null && it.longitude != null && it.status == RequestStatus.PENDING }.forEach { req ->
            val formattedDate = req.createdAt.format(formatter)
            map.addMarker(
                MarkerOptions()
                    .position(LatLng(req.latitude!!, req.longitude!!))
                    .title("Area: ${req.areaId} (${req.severity})")
                    .snippet("${req.description} - Date/Time: $formattedDate")
            )
        }
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