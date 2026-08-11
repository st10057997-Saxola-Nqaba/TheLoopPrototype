package com.example.theloopprototype.data

import com.example.theloopprototype.models.DArea
import com.example.theloopprototype.models.DAnimalType
import com.example.theloopprototype.models.DIllnessType

object DummyLookups {
    val areas = listOf(
        DArea("area1", "Olievenhoutbosch"),
        DArea("area2", "Tembisa"),
        DArea("area3", "Ivory Park"),
        DArea("area4", "Diepsloot"),
    )

    val animalTypes = listOf(
        DAnimalType("at1", "Dog"),
        DAnimalType("at2", "Cat"),
        DAnimalType("at3", "Horse"),
        DAnimalType("at4", "Donkey"),
        DAnimalType("at5", "Cattle"),
    )

    val illnessTypes = listOf(
        DIllnessType("it1", "Mange"),
        DIllnessType("it2", "Tick-bite Fever"),
        DIllnessType("it3", "Malnutrition"),
        DIllnessType("it4", "Wound / Injury"),
        DIllnessType("it5", "Respiratory Infection"),
        DIllnessType("it6", "Intestinal Parasites"),
    )
}
