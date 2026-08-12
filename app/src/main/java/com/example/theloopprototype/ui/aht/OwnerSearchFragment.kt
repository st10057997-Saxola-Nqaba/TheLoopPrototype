package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyUsers

class OwnerSearchFragment : Fragment(R.layout.fragment_owner_search) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val etSearchQuery = view.findViewById<EditText>(R.id.etSearchQuery)
        val btnSearch = view.findViewById<Button>(R.id.btnSearch)

        btnSearch.setOnClickListener {
            val query = etSearchQuery.text.toString().trim()
            if (query.isBlank()) {
                Toast.makeText(requireContext(), "Please enter a search term", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Search existing users by cellphone number or address
            val existingUser = DummyUsers.users.find {
                it.cellphoneNumber.contains(query, ignoreCase = true) ||
                        (it.physicalAddress?.contains(query, ignoreCase = true) == true)
            }

            if (existingUser != null) {
                val bundle = Bundle().apply {
                    putString("ownerId", existingUser.id)
                }
                findNavController().navigate(R.id.action_ownerSearchFragment_to_ownerDetailFragment, bundle)
            } else {
                AlertDialog.Builder(requireContext())
                    .setTitle("Owner Not Found")
                    .setMessage("No user profile matches this search. Would you like to create a new profile?")
                    .setPositiveButton("Yes") { _, _ ->
                        val bundle = Bundle().apply {
                            putString("initialPhone", query)
                        }
                        findNavController().navigate(R.id.action_ownerSearchFragment_to_createUserPetFragment, bundle)
                    }
                    .setNegativeButton("No", null)
                    .show()
            }
        }
    }
}