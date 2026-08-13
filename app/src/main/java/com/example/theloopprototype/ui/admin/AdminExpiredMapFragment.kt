package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.RequestStatus
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class AdminExpiredMapFragment : Fragment(R.layout.fragment_admin_expired_map) {

    private var mapViewExpired: MapView? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mapViewExpired = view.findViewById(R.id.mapViewExpired)
        mapViewExpired?.onCreate(savedInstanceState)

        val tvSummary = view.findViewById<TextView>(R.id.tvExpiredSummary)
        val btnBack = view.findViewById<Button>(R.id.btnBackFromExpiredMap)

        btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        // Calculate summary statistics matching your card description
        val expiredList = DummyRequests.requests.filter { it.status == RequestStatus.EXPIRED }
        val groupedByArea = expiredList.groupBy { it.areaId }
        val summaryText = StringBuilder("Total Expired: ${expiredList.size}\n")
        groupedByArea.forEach { (area, reqs) ->
            val severities = reqs.groupingBy { it.severity }.eachCount()
            summaryText.append("Area $area: ${reqs.size} requests ($severities)\n")
        }
        tvSummary.text = summaryText.toString().trim()

        mapViewExpired?.getMapAsync { map ->
            val tembisa = LatLng(-25.9987, 28.2201)
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(tembisa, 12f))

            // Plot only expired requests with specific styling/markers
            expiredList.filter { it.latitude != null && it.longitude != null }.forEach { req ->
                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(req.latitude!!, req.longitude!!))
                        .title("EXPIRED - Area: ${req.areaId} (${req.severity})")
                        .snippet(req.description)
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mapViewExpired?.onResume()
    }

    override fun onPause() {
        mapViewExpired?.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapViewExpired?.onDestroy()
        super.onDestroy()
    }
}