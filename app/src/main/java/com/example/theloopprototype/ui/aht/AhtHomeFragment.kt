package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.R
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.data.DummyRequests
import com.example.theloopprototype.models.RequestStatus
import com.example.theloopprototype.models.DScheduledRequestList
import com.example.theloopprototype.models.ScheduleStatus
import com.example.theloopprototype.adapter.ScheduledRequestAdapter

class AhtHomeFragment : Fragment(R.layout.fragment_aht_home) {

    private var isMapView = false
    private var currentFilter: RequestStatus = RequestStatus.SCHEDULED

    private lateinit var requestAdapter: ScheduledRequestAdapter
    private val displayedLists = mutableListOf<DScheduledRequestList>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewRequests)
        val btnToggleView = view.findViewById<Button>(R.id.btnToggleView)
        val btnOpenSearch = view.findViewById<Button>(R.id.btnOpenSearch)
        val mapContainer = view.findViewById<View>(R.id.mapContainer)

        // Bind Counter TextViews & Cards
        val tvPendingCount = view.findViewById<TextView>(R.id.tvPendingCount)
        val tvScheduledCount = view.findViewById<TextView>(R.id.tvScheduledCount)
        val tvFulfilledCount = view.findViewById<TextView>(R.id.tvFulfilledCount)

        val cardPending = view.findViewById<CardView>(R.id.cardPending)
        val cardScheduled = view.findViewById<CardView>(R.id.cardScheduled)
        val cardFulfilled = view.findViewById<CardView>(R.id.cardFulfilled)

        // Compute and display counts from DummyRequests
        val pendingCount = DummyRequests.requests.count { it.status == RequestStatus.PENDING }
        val scheduledCount = DummyRequests.requests.count { it.status == RequestStatus.SCHEDULED }
        val fulfilledCount = DummyRequests.requests.count { it.status == RequestStatus.FULFILLED }

        tvPendingCount?.text = pendingCount.toString()
        tvScheduledCount?.text = scheduledCount.toString()
        tvFulfilledCount?.text = fulfilledCount.toString()

        // Setup RecyclerView & Filter Handlers
        if (recyclerView != null) {
            recyclerView.layoutManager = LinearLayoutManager(requireContext())
            val areas = DummyData.areas ?: emptyList()

            // 1. Initialize the adapter ONCE with our mutable list reference
            requestAdapter = ScheduledRequestAdapter(displayedLists, areas) { clickedSchedule ->
                val bundle = Bundle().apply {
                    putString("targetListId", clickedSchedule.id)
                }
                findNavController().navigate(R.id.action_ahtHomeFragment_to_viewScheduledRequestFragment, bundle)
            }
            recyclerView.adapter = requestAdapter

            // 2. Function to update dataset and notify adapter changes
            fun loadFilteredData() {
                displayedLists.clear()

                val newItems = when (currentFilter) {
                    RequestStatus.SCHEDULED -> {
                        DummyData.scheduledRequestLists?.toMutableList() ?: mutableListOf()
                    }
                    RequestStatus.PENDING, RequestStatus.FULFILLED -> {
                        DummyRequests.requests
                            .filter { it.status == currentFilter }
                            .map { req ->
                                DScheduledRequestList(
                                    id = req.id,
                                    areaId = req.areaId,
                                    adminId = req.ownerId,
                                    scheduleDate = req.createdAt,
                                    status = ScheduleStatus.CONFIRMED
                                )
                            }.toMutableList()
                    }
                    else -> DummyData.scheduledRequestLists?.toMutableList() ?: mutableListOf()
                }

                displayedLists.addAll(newItems)
                requestAdapter.notifyDataSetChanged() // Refreshes the UI cards instantly
            }

            // Load initial view (Scheduled)
            loadFilteredData()

            // Card Click Listeners to switch data filter dynamically
            cardPending?.setOnClickListener {
                currentFilter = RequestStatus.PENDING
                Toast.makeText(requireContext(), "Showing Pending Requests ($pendingCount)", Toast.LENGTH_SHORT).show()
                loadFilteredData()
            }

            cardScheduled?.setOnClickListener {
                currentFilter = RequestStatus.SCHEDULED
                Toast.makeText(requireContext(), "Showing Scheduled Outreaches ($scheduledCount)", Toast.LENGTH_SHORT).show()
                loadFilteredData()
            }

            cardFulfilled?.setOnClickListener {
                currentFilter = RequestStatus.FULFILLED
                Toast.makeText(requireContext(), "Showing Fulfilled History ($fulfilledCount)", Toast.LENGTH_SHORT).show()
                loadFilteredData()
            }

            // Drag-and-Drop support for rows
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
                    requestAdapter.onItemMove(fromPosition, toPosition)
                    return true
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}
                override fun isLongPressDragEnabled(): Boolean = currentFilter == RequestStatus.SCHEDULED
            }

            val itemTouchHelper = ItemTouchHelper(touchHelperCallback)
            itemTouchHelper.attachToRecyclerView(recyclerView)
        }

        // Setup Search Button click listener
        btnOpenSearch?.setOnClickListener {
            findNavController().navigate(R.id.action_ahtHomeFragment_to_ownerSearchFragment)
        }

        // Setup Toggle Button Behavior
        btnToggleView?.setOnClickListener {
            isMapView = !isMapView

            if (isMapView) {
                btnToggleView.text = "List View"
                recyclerView.visibility = View.GONE
                mapContainer.visibility = View.VISIBLE

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