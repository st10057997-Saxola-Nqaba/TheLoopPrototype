package com.example.theloopprototype.models

import java.time.LocalDate
import java.time.LocalDateTime

data class DVisitSummary(
    val visitId: String,
    val petOwnerName: String,
    val visitOutcome: String,
    val flagsPosted: String,
    val detailedNotes: String = "All vital checks performed standard procedure followed.",
    val officerId: String = "AHT-Officer-42",
    val timestamp: LocalDateTime = LocalDateTime.now().minusDays(2)
)

data class DOutreachOutcome(
    val id: String,
    val initiativeName: String,
    val areaId: String,
    val flagSummary: String,
    val totalFlags: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val visitSummaries: List<DVisitSummary> = emptyList()
)