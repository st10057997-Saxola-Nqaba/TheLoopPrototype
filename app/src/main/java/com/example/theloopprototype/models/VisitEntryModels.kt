package com.example.theloopprototype.models

import java.time.LocalDateTime

data class DIllnessType(
    val id: String,
    val typeName: String
)

data class DVisitEntry(
    val id: String,
    val petId: String,
    val requestId: String?,
    val ahtId: String,
    val visitDateTime: LocalDateTime,
    val reasonForVisit: String,
    val outcome: String,
    val prescribedAction: String,
    val illnessFlag: Boolean,
    val illnessTypeId: String?,
    val illnessDescription: String?,
    val returnVisitFlag: Boolean
)