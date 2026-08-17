package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.Toast
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.models.DScheduledRequestList
import com.example.theloopprototype.models.ScheduleStatus
import com.google.android.gms.maps.model.Marker
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.RequestStatus
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.pow

class AdminMapPickerFragment : Fragment(R.layout.fragment_admin_map_picker), OnMapReadyCallback {

    private var mapView: MapView? = null
    private var googleMap: GoogleMap? = null
    private val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm")

    private var pinnedLocation: LatLng? = null
    private var resolvedAreaId: String? = null
    private var dropPinMarker: Marker? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapView = view.findViewById(R.id.mapViewCluster)
        mapView?.onCreate(savedInstanceState)
        mapView?.getMapAsync(this)

        val tvSelectedLocation = view.findViewById<TextView>(R.id.tvSelectedLocation)
        val btnConfirm = view.findViewById<Button>(R.id.btnConfirmPinLocation)
        val btnBack = view.findViewById<ImageButton>(R.id.btnBack)

        tvSelectedLocation.text = "No location selected"

        // Handle Back Button Action
        btnBack?.setOnClickListener {
            findNavController().popBackStack()
        }

        // Creates the list (pin + nearest area)
        btnConfirm?.setOnClickListener {
            val areaId = resolvedAreaId

            if (pinnedLocation == null || areaId == null) {
                Toast.makeText(requireContext(), "Tap a point on the map to select a location first.", Toast.LENGTH_LONG).show()
                return@setOnClickListener
            }

            val newId = "srl_${System.currentTimeMillis()}"
            val newList = DScheduledRequestList(
                id = newId,
                areaId = areaId,
                adminId = "u8",
                scheduleDate = LocalDateTime.now().plusDays(3),
                status = ScheduleStatus.CONFIRMED
            )

            DummyRequests.scheduledRequestLists.add(newList)
            val movedCount = DummyRequests.linkPendingRequestsToSchedule(areaId, newId)

            Toast.makeText(requireContext(), "Schedule created for $areaId , $movedCount pending requests moved to schedule", Toast.LENGTH_LONG).show()
            findNavController().navigate(R.id.adminSchedulesFragment)
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

        // Finds the nearest area with pending requests
        map.setOnMapClickListener { tapped ->
            dropPinMarker?.remove()
            dropPinMarker = map.addMarker(MarkerOptions().position(tapped).title("Selected Schedule Location"))
            pinnedLocation = tapped

            val nearestPending = DummyRequests.requests
                .filter { it.latitude != null && it.longitude != null && it.status == RequestStatus.PENDING }
                .minByOrNull { req ->
                    val dLat = req.latitude!! - tapped.latitude
                    val dLng = req.longitude!! - tapped.longitude
                    dLat.pow(2) + dLng.pow(2)
                }

            resolvedAreaId = nearestPending?.areaId
            val label = resolvedAreaId ?: "No nearby serviced area found"
            view?.findViewById<TextView>(R.id.tvSelectedLocation)?.text = "Selected area: $label (${"%.4f".format(tapped.latitude)}, ${"%.4f".format(tapped.longitude)})"
        }
    }

    // Code Attribution
    // This method was taken from – Google Maps SDK for Android (official documentation)
    // Link – https://developers.google.com/android/reference/com/google/android/gms/maps/MapView
    // Authors Name and Surname – Google

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