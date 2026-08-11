package com.example.theloopprototype.models

import java.time.LocalDateTime

data class DRequest(
    val id: String,
    val ownerId: String,
    val petId: String?,
    val areaId: String,
    val severity: Severity,
    val description: String,
    val status: RequestStatus,
    val latitude: Double?,
    val longitude: Double?,
    val generatedFromVisitEntryId: String?,
    val createdAt: LocalDateTime,
    val expirationDateTime: LocalDateTime
)

data class DScheduledRequestList(
    val id: String,
    val areaId: String,
    val adminId: String,
    val scheduleDate: LocalDateTime,
    val status: ScheduleStatus
)

data class DRequestListItem(
    val id: String,
    val scheduleRequestListId: String,
    val requestId: String,
    val sortOrder: Int
)