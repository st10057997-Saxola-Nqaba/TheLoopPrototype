package com.example.theloopprototype.models


enum class Role { OWNER, AHT, ADMIN }
enum class Severity { LOW, MEDIUM, HIGH }
enum class RequestStatus { PENDING, SCHEDULED, FULFILLED, EXPIRED }
enum class ScheduleStatus { DRAFT, CONFIRMED }
enum class NotificationType { OUTREACH, REQUEST_STATUS }