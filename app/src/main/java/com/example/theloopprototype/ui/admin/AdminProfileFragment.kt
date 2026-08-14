package com.example.theloopprototype.ui.admin

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R

class AdminProfileFragment : Fragment(R.layout.fragment_admin_profile) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.btnEditProfile).setOnClickListener {
            findNavController().navigate(R.id.action_adminProfileFragment_to_adminEditProfileFragment)
        }

        view.findViewById<Button>(R.id.btnDeleteProfile).setOnClickListener {
            Toast.makeText(requireContext(), "Profile deletion requested", Toast.LENGTH_SHORT).show()
        }

        view.findViewById<Button>(R.id.btnLogout).setOnClickListener {
            findNavController().navigate(R.id.action_adminProfileFragment_to_loginFragment)
        }
    }
}