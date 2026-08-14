package com.example.theloopprototype.models

import java.time.LocalDate

data class DOutreachOutcome(
    val id: String,
    val initiativeName: String,
    val areaId: String,
    val flagSummary: String,
    val totalFlags: Int,
    val startDate: LocalDate,
    val endDate: LocalDate
)