package com.example.theloopprototype.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.R
import com.example.theloopprototype.data.DummyPets
import com.example.theloopprototype.databinding.ItemRequestBinding
import com.example.theloopprototype.models.DRequest
import com.example.theloopprototype.models.RequestStatus
import com.example.theloopprototype.models.Severity
import java.time.format.DateTimeFormatter

class RequestAdapter(
    private val requests: List<DRequest>,
    private val onRequestClick: (DRequest) -> Unit
) : RecyclerView.Adapter<RequestAdapter.RequestViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RequestViewHolder {
        val binding = ItemRequestBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return RequestViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RequestViewHolder, position: Int) {
        val request = requests[position]
        holder.bind(request)
        holder.itemView.setOnClickListener { onRequestClick(request) }
    }

    override fun getItemCount(): Int = requests.size

    class RequestViewHolder(
        private val binding: ItemRequestBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(request: DRequest) {
            // Get pet name if exists
            val pet = DummyPets.getPetById(request.petId)
            val petName = pet?.name ?: "Unknown Pet"

            binding.tvPetName.text = petName
            binding.tvDescription.text = request.description
            binding.tvSeverity.text = request.severity.name

            // Set severity color
            val severityColor = when (request.severity) {
                Severity.LOW -> android.graphics.Color.parseColor("#4CAF50")
                Severity.MEDIUM -> android.graphics.Color.parseColor("#FF9800")
                Severity.HIGH -> android.graphics.Color.parseColor("#F44336")
            }
            binding.tvSeverity.setTextColor(severityColor)

            // Set status
            binding.tvStatus.text = request.status.name

            // Set status style
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

            // Format date
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")
            binding.tvCreatedDate.text = request.createdAt.format(formatter)
        }
    }
}