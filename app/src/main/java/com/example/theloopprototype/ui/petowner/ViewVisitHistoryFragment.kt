package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.adapter.VisitEntryAdapter
import com.example.theloopprototype.databinding.FragmentViewVisitHistoryBinding

class ViewVisitHistoryFragment : Fragment() {

    private var _binding: FragmentViewVisitHistoryBinding? = null
    private val binding get() = _binding!!
    private val args: ViewVisitHistoryFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentViewVisitHistoryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val pet = DummyData.getPetById(args.petId)
        if (pet == null) {
            findNavController().popBackStack()
            return
        }

        setupUI(pet)
        setupVisitHistory(pet.id)
        setupClickListeners()
    }

    private fun setupUI(pet: com.example.theloopprototype.models.DPet) {
        binding.tvPetName.text = pet.name
        binding.tvPetBreed.text = "${pet.breed} • ${pet.sex}"
    }

    private fun setupVisitHistory(petId: String) {
        val visitEntries = DummyData.getVisitEntriesForPet(petId)
        val adapter = VisitEntryAdapter(visitEntries)

        binding.rvVisitHistory.apply {
            layoutManager = LinearLayoutManager(context)
            this.adapter = adapter
        }

        binding.tvVisitCount.text = "${visitEntries.size} visit(s)"

        if (visitEntries.isEmpty()) {
            binding.tvNoHistory.visibility = View.VISIBLE
            binding.rvVisitHistory.visibility = View.GONE
        } else {
            binding.tvNoHistory.visibility = View.GONE
            binding.rvVisitHistory.visibility = View.VISIBLE
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}