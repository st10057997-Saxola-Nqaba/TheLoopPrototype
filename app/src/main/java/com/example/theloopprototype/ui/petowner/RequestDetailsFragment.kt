package com.example.theloopprototype.ui.petowner

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.R
import com.example.theloopprototype.databinding.FragmentRequestDetailsBinding
import com.example.theloopprototype.models.DRequest
import com.example.theloopprototype.models.RequestStatus
import com.example.theloopprototype.models.Severity
import java.time.format.DateTimeFormatter

class RequestDetailsFragment : Fragment() {

    private var _binding: FragmentRequestDetailsBinding? = null
    private val binding get() = _binding!!
    private val args: RequestDetailsFragmentArgs by navArgs()

    private lateinit var request: DRequest

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRequestDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Load request data
        request = DummyData.getRequestById(args.requestId) ?: run {
            // Handle not found
            findNavController().popBackStack()
            return
        }

        displayRequestDetails()
        setupClickListeners()
    }

    private fun displayRequestDetails() {
        val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")

        // Pet name
        val pet = DummyData.getPetById(request.petId ?: "")
        binding.tvPetName.text = pet?.name ?: "Unknown Pet"

        // Request ID
        binding.tvRequestId.text = "Request #${request.id.take(8)}"

        // Status
        binding.tvStatus.text = request.status.name
        when (request.status) {
            RequestStatus.PENDING -> {
                binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#FF9800"))
                binding.tvStatus.setBackgroundResource(R.drawable.bg_status_pending)
            }
            RequestStatus.SCHEDULED -> {
                binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#2196F3"))
                binding.tvStatus.setBackgroundResource(R.drawable.bg_status_scheduled)
            }
            RequestStatus.FULFILLED -> {
                binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#4CAF50"))
                binding.tvStatus.setBackgroundResource(R.drawable.bg_status_fulfilled)
            }
            RequestStatus.EXPIRED -> {
                binding.tvStatus.setTextColor(android.graphics.Color.parseColor("#9E9E9E"))
                binding.tvStatus.setBackgroundResource(R.drawable.bg_status_expired)
            }
        }

        // Severity with color
        val severityColor = when (request.severity) {
            Severity.LOW -> android.graphics.Color.parseColor("#4CAF50")
            Severity.MEDIUM -> android.graphics.Color.parseColor("#FF9800")
            Severity.HIGH -> android.graphics.Color.parseColor("#F44336")
        }
        binding.tvSeverity.text = "Severity: ${request.severity.name}"
        binding.tvSeverity.setTextColor(severityColor)

        // Description
        binding.tvDescription.text = request.description

        // Area
        binding.tvArea.text = "Area: ${request.areaId}"

        // Dates
        binding.tvCreatedDate.text = "Created: ${request.createdAt.format(formatter)}"
        binding.tvExpiryDate.text = "Expires: ${request.expirationDateTime.format(formatter)}"

        // Check if fulfilled and has visit entry
        if (request.status == RequestStatus.FULFILLED) {
            val visitEntry = DummyData.getVisitEntryByRequestId(request.id)
            if (visitEntry != null) {
                binding.btnViewVisit.visibility = View.VISIBLE
            }
        } else {
            binding.btnViewVisit.visibility = View.GONE
        }
    }

    private fun setupClickListeners() {
        binding.btnBack.setOnClickListener {
            findNavController().popBackStack()
        }

        binding.btnViewVisit.setOnClickListener {
            // Navigate to ViewVisitHistoryFragment or ViewPetFragment
            findNavController().navigate(
                R.id.action_requestDetailsFragment_to_viewVisitHistoryFragment
            )
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}