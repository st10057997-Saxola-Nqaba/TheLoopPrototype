package com.example.theloopprototype.data

import com.example.theloopprototype.models.DPet
import java.time.LocalDate

object DummyPets {
    val pets = listOf(
        DPet("p1", "u1", "at1", "Rex", "Boerboel Cross", "Male", LocalDate.of(2021, 4, 12), 32.5, 60.0, true),
        DPet("p2", "u1", "at2", "Whiskers", "Domestic Shorthair", "Female", LocalDate.of(2022, 9, 3), 3.8, 24.0, true),
        DPet("p3", "u2", "at3", "Duke", "Boerperd", "Male", null, 420.0, 152.0, false),
        DPet("p4", "u2", "at4", "Bella", "Donkey (mixed)", "Female", null, 130.0, 100.0, true),
        DPet("p5", "u3", "at1", "Max", "Ridgeback Cross", "Male", LocalDate.of(2020, 1, 20), 28.0, 58.0, false),
        DPet("p6", "u4", "at2", "Coco", "Domestic Shorthair", "Female", LocalDate.of(2023, 6, 15), 3.2, 22.0, false),
        DPet("p7", "u5", "at3", "Storm", "Boerperd", "Female", null, 380.0, 148.0, false),
        DPet("p8", "u5", "at1", "Buttons", "Mixed Breed", "Male", LocalDate.of(2024, 2, 1), 14.0, 40.0, false),
    )
    // pet owner screen funtion

    fun getPetsForOwner(ownerId: String): List<DPet> {
        return pets.filter { it.ownerId == ownerId }
    }

    fun getPetById(petId: String?): DPet? {
        if (petId == null) return null
        return pets.firstOrNull { it.id == petId }
    }

    fun getAllPets(): List<DPet> {
        return pets
    }
}
