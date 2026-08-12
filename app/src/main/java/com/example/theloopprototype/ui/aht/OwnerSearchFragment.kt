package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.R
import com.example.theloopprototype.data.*

class OwnerSearchFragment : Fragment(R.layout.fragment_owner_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearchQuery = view.findViewById<EditText>(R.id.etSearchQuery)
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)
        val recyclerView = view.findViewById<RecyclerView>(R.id.recyclerViewSearchResults)

        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        btnSearch.setOnClickListener {
            val query = etSearchQuery.text.toString().trim().lowercase()

            if (query.isBlank()) {
                Toast.makeText(requireContext(), "Please enter a cellphone number or address", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Search users by cellphone number or address from Dummy data
            val matchingUsers = DummyUsers.users.filter { user ->
                val phoneMatch = user.cellphoneNumber.lowercase().contains(query)
                // Add address filtering if your user model supports it, e.g.:
                // val addressMatch = user.address?.lowercase()?.contains(query) == true
                phoneMatch // || addressMatch
            }

            if (matchingUsers.isEmpty()) {
                Toast.makeText(requireContext(), "No owners found matching query", Toast.LENGTH_SHORT).show()
            } else {
                // TODO: Bind matching users to an adapter to display results in the RecyclerView
                Toast.makeText(requireContext(), "Found ${matchingUsers.size} owner(s)", Toast.LENGTH_SHORT).show()
            }
        }
    }
}