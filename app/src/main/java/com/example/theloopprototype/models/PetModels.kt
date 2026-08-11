package com.example.theloopprototype.models

import java.time.LocalDate

data class DAnimalType(
    val id: String,
    val typeName: String
)

data class DPet(
    val id: String,
    val ownerId: String,
    val animalTypeId: String,
    val name: String,
    val breed: String,
    val sex: String,
    val dateOfBirth: LocalDate?,
    val weightKg: Double,
    val heightCm: Double,
    val isSterilised: Boolean
)