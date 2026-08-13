package com.example.theloopprototype.data

import com.example.theloopprototype.models.DIllnessType

object DummyIllnessTypes {
    private val illnessTypes = listOf(
        DIllnessType("ill1", "Canine Distemper"),
        DIllnessType("ill2", "Parvovirus"),
        DIllnessType("ill3", "Rabies"),
        DIllnessType("ill4", "Feline Leukemia"),
        DIllnessType("ill5", "Mange"),
        DIllnessType("ill6", "Tick Bite Fever"),
        DIllnessType("ill7", "Respiratory Infection"),
        DIllnessType("ill8", "Wound Infection"),
        DIllnessType("ill9", "Malnutrition"),
        DIllnessType("ill10", "Other")
    )

    fun getIllnessTypes(): List<DIllnessType> = illnessTypes

    fun getIllnessTypeById(id: String?): String? {
        if (id == null) return null
        return illnessTypes.firstOrNull { it.id == id }?.typeName
    }
}