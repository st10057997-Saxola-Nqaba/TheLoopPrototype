package com.example.theloopprototype.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.theloopprototype.DummyData
import com.example.theloopprototype.databinding.ItemPetBinding
import com.example.theloopprototype.models.DPet

class PetAdapter(
    private val pets: List<DPet>,
    private val onPetClick: (DPet) -> Unit
) : RecyclerView.Adapter<PetAdapter.PetViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PetViewHolder {
        val binding = ItemPetBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return PetViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PetViewHolder, position: Int) {
        val pet = pets[position]
        holder.bind(pet)
        holder.itemView.setOnClickListener { onPetClick(pet) }
    }

    override fun getItemCount(): Int = pets.size

    class PetViewHolder(
        private val binding: ItemPetBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(pet: DPet) {
            val animalType = DummyData.animalTypes
                .firstOrNull { it.id == pet.animalTypeId }
                ?.typeName ?: "Unknown"

            binding.tvPetName.text = pet.name
            binding.tvPetType.text = animalType
            binding.tvPetBreed.text = pet.breed
            binding.tvPetGender.text = pet.sex

            // Show sterilised status icon
            binding.ivSterilised.visibility = if (pet.isSterilised) {
                android.view.View.VISIBLE
            } else {
                android.view.View.GONE
            }
        }
    }
}