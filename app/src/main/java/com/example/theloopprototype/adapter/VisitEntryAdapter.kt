// Code Attribution
// This method was taken from Stack Overflow
// https://stackoverflow.com/questions/40584424/simple-android-recyclerview-example
// Suragch
// https://stackoverflow.com/users/3681880/suragch
package com.example.theloopprototype.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.data.DummyIllnessTypes
import com.example.theloopprototype.databinding.ItemVisitEntryBinding
import com.example.theloopprototype.models.DVisitEntry
import java.time.format.DateTimeFormatter

class VisitEntryAdapter(
    private val visitEntries: List<DVisitEntry>
) : RecyclerView.Adapter<VisitEntryAdapter.VisitEntryViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VisitEntryViewHolder {
        val binding = ItemVisitEntryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return VisitEntryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: VisitEntryViewHolder, position: Int) {
        val entry = visitEntries[position]
        holder.bind(entry)
    }

    override fun getItemCount(): Int = visitEntries.size

    class VisitEntryViewHolder(
        private val binding: ItemVisitEntryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(entry: DVisitEntry) {
            val formatter = DateTimeFormatter.ofPattern("dd MMM yyyy, HH:mm")

            binding.tvVisitDate.text = entry.visitDateTime.format(formatter)
            binding.tvReason.text = entry.reasonForVisit
            binding.tvOutcome.text = "Outcome: ${entry.outcome}"
            binding.tvPrescribedAction.text = "Prescribed: ${entry.prescribedAction}"

            // Illness flag
            if (entry.illnessFlag && entry.illnessTypeId != null) {
                val illnessName = DummyIllnessTypes.getIllnessTypeById(entry.illnessTypeId) ?: "Unknown"
                binding.tvIllnessFlag.visibility = View.VISIBLE
                binding.tvIllnessFlag.text = "⚠️ $illnessName"
                binding.tvIllnessFlag.setTextColor(android.graphics.Color.parseColor("#F44336"))
            } else {
                binding.tvIllnessFlag.visibility = View.GONE
            }

            // Return visit flag
            if (entry.returnVisitFlag) {
                binding.tvReturnVisit.visibility = View.VISIBLE
                binding.tvReturnVisit.text = "🔄 Return visit requested"
                binding.tvReturnVisit.setTextColor(android.graphics.Color.parseColor("#FF9800"))
            } else {
                binding.tvReturnVisit.visibility = View.GONE
            }

            // Show if linked to a request
            if (entry.requestId != null) {
                binding.tvLinkedRequest.visibility = View.VISIBLE
                binding.tvLinkedRequest.text = "Linked to request"
            } else {
                binding.tvLinkedRequest.visibility = View.GONE
            }
        }
    }
}