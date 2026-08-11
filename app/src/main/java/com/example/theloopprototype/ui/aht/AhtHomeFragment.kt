package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.R
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.adapter.ScheduledRequestAdapter

class AhtHomeFragment : Fragment(R.layout.fragment_aht_home) {

    private var isMapView = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRequests)
        val btnToggleView = view.findViewById<Button>(R.id.btnToggleView)
        val mapContainer = view.findViewById<View>(R.id.mapContainer)

        // Setup RecyclerView & Drag-and-Drop
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())

            val scheduledLists = DummyData.scheduledRequestLists?.toMutableList() ?: mutableListOf()
            val areas = DummyData.areas ?: emptyList()

            val adapter = ScheduledRequestAdapter(scheduledLists, areas)
            recyclerView.adapter = adapter

            val touchHelperCallback = object : ItemTouchHelper.SimpleCallback(
                ItemTouchHelper.UP or ItemTouchHelper.DOWN or ItemTouchHelper.START or ItemTouchHelper.END,
                0
            ) {
                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    val fromPosition = viewHolder.adapterPosition
                    val toPosition = target.adapterPosition
                    adapter.onItemMove(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    // Not used
                }

                override fun isLongPressDragEnabled(): Boolean = true

                override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
                    super.onSelectedChanged(viewHolder, actionState)
                    if (actionState == ItemTouchHelper.ACTION_STATE_DRAG) {
                        viewHolder?.itemView?.alpha = 0.7f
                        viewHolder?.itemView?.scaleX = 1.02f
                        viewHolder?.itemView?.scaleY = 1.02f
                    }
                }

                override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
                    super.clearView(recyclerView, viewHolder)
                    viewHolder.itemView.alpha = 1.0f
                    viewHolder.itemView.scaleX = 1.0f
                    viewHolder.itemView.scaleY = 1.0f
                }
            }

            val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

        // Setup Toggle Button Behavior
        btnToggleView?.setOnClickListener {
            isMapView = !isMapView

            if (isMapView) {
                btnToggleView.text = "List View"
                recyclerView.visibility = View.GONE
                mapContainer.visibility = View.VISIBLE

                // Load the Map Fragment into the container if not already loaded
                childFragmentManager.findFragmentByTag("MAP_FRAGMENT") ?: run {
                    childFragmentManager.beginTransaction()
                        .replace(R.id.mapContainer, AhtMapFragment(), "MAP_FRAGMENT")
                        .commit()
                }
            } else {
                btnToggleView.text = "Map View"
                recyclerView.visibility = View.VISIBLE
                mapContainer.visibility = View.GONE
            }
        }
    }
}