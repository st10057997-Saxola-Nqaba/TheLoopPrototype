package com.example.theloopprototype.ui.aht

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyPets
import com.example.theloopprototype.data.DummyUsers
import com.google.android.material.appbar.MaterialToolbar

class OwnerDetailFragment : Fragment(R.layout.fragment_owner_detail) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val toolbar = view.findViewById<MaterialToolbar>(R.id.toolbarOwnerDetail)
        toolbar?.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val ownerId = arguments?.getString("ownerId") ?: return

        val owner = DummyUsers.users.find { it.id == ownerId }
        val tvOwnerName = view.findViewById<TextView>(R.id.tvOwnerName)
        val tvOwnerPhone = view.findViewById<TextView>(R.id.tvOwnerPhone)
        val tvOwnerEmail = view.findViewById<TextView>(R.id.tvOwnerEmail)
        val tvOwnerAddress = view.findViewById<TextView>(R.id.tvOwnerAddress)

        val btnCreateVisitEntry = view.findViewById<Button>(R.id.btnCreateVisitEntry)

        tvOwnerName.text = "${owner?.firstName} ${owner?.lastName}"
        tvOwnerPhone.text = owner?.cellphoneNumber
        tvOwnerEmail.text = owner?.emailAddress ?: "N/A"
        tvOwnerAddress.text = owner?.physicalAddress ?: "N/A"

        btnCreateVisitEntry.setOnClickListener {
            val bundle = Bundle().apply {
                putString("ownerId", ownerId)
            }
            findNavController().navigate(R.id.action_ownerDetailFragment_to_createVisitEntryFragment, bundle)
        }
    }
}