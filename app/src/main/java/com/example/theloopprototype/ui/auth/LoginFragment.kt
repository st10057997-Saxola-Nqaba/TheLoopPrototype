package com.example.theloopprototype.ui.auth

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.DummyData

class LoginFragment : Fragment(R.layout.fragment_login) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // You can verify dummy data is accessible right away:
        val allUsers = DummyData.users

        val btnLoginAht = view.findViewById<Button>(R.id.btnLoginAht)
        val btnLoginOwner = view.findViewById<Button>(R.id.btnLoginOwner)

        // Action when logging in as AHT
        btnLoginAht.setOnClickListener {
            // Navigates using the action defined in your nav_graph.xml
            findNavController().navigate(R.id.action_login_to_aht_home)
        }

        // Action when logging in as Pet Owner
        btnLoginOwner.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_owner_home)
        }
    }
}