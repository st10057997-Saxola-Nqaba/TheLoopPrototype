package com.example.theloopprototype.models

import java.time.LocalDateTime

data class DNotification(
    val id: String,
    val areaId: String?,
    val adminId: String,
    val message: String,
    val type: NotificationType,
    val sentAt: LocalDateTime
)
