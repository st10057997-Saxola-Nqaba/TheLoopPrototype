package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.theloopprototype.R
import com.example.theloopprototype.databinding.FragmentPetOwnerProfileBinding

class PetOwnerProfileFragment : Fragment() {

    private var _binding: FragmentPetOwnerProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPetOwnerProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupClickListeners()
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnEditProfile.setOnClickListener {
            // Navigate to edit profile
        }

        binding.btnDeleteProfile.setOnClickListener {
            // Show confirmation dialog then delete
        }

        binding.btnLogout.setOnClickListener {
            findNavController().navigate(R.id.action_petOwnerProfileFragment_to_loginFragment)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}