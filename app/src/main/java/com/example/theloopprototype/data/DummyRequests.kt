package com.example.theloopprototype.data

import com.example.theloopprototype.models.*
import java.time.LocalDateTime

object DummyRequests {
    val requests = listOf(
        DRequest("r1", "u1", "p1", "area2", Severity.HIGH, "Limping on front-left leg since yesterday",
            RequestStatus.PENDING, -25.9987, 28.2201, null,
            LocalDateTime.of(2026, 8, 9, 8, 0), LocalDateTime.of(2026, 8, 16, 8, 0)),
        DRequest("r2", "u2", "p3", "area3", Severity.MEDIUM, "Hoof check needed before working season",
            RequestStatus.SCHEDULED, -25.9432, 28.2461, null,
            LocalDateTime.of(2026, 8, 3, 9, 0), LocalDateTime.of(2026, 8, 17, 9, 0)),
        DRequest("r3", "u3", "p5", "area4", Severity.LOW, "Routine deworming due",
            RequestStatus.FULFILLED, -25.9331, 27.9269, null,
            LocalDateTime.of(2026, 7, 28, 11, 0), LocalDateTime.of(2026, 8, 4, 11, 0)),
        DRequest("r4", "u4", "p6", "area1", Severity.MEDIUM, "Skin rash around ears, worsening",
            RequestStatus.EXPIRED, -25.9755, 27.9012, null,
            LocalDateTime.of(2026, 7, 15, 10, 0), LocalDateTime.of(2026, 7, 22, 10, 0)),
        DRequest("r5", "u5", "p7", "area2", Severity.HIGH, "Possible colic — off food, lying down often",
            RequestStatus.PENDING, -25.9986, 28.2185, null,
            LocalDateTime.of(2026, 8, 10, 7, 30), LocalDateTime.of(2026, 8, 17, 7, 30)),
        DRequest("r6", "u3", null, "area4", Severity.HIGH, "Stray dog hit by car near our yard, needs urgent help",
            RequestStatus.PENDING, -25.9340, 27.9280, null,
            LocalDateTime.of(2026, 8, 10, 16, 0), LocalDateTime.of(2026, 8, 17, 16, 0)),
        DRequest("r7", "u5", "p8", "area2", Severity.LOW, "Vaccination due",
            RequestStatus.SCHEDULED, -25.9990, 28.2210, null,
            LocalDateTime.of(2026, 8, 4, 9, 0), LocalDateTime.of(2026, 8, 18, 9, 0)),
        DRequest("r8", "u1", "p1", "area2", Severity.MEDIUM, "Return visit — mange follow-up check",
            RequestStatus.PENDING, -25.9987, 28.2201, "v2",
            LocalDateTime.of(2026, 8, 6, 13, 45), LocalDateTime.of(2026, 8, 20, 13, 45)),
    )

    val scheduledRequestLists = listOf(
        DScheduledRequestList("srl1", "area3", "u8", LocalDateTime.of(2026, 8, 17, 8, 0), ScheduleStatus.CONFIRMED),
        DScheduledRequestList("srl2", "area2", "u8", LocalDateTime.of(2026, 8, 18, 8, 0), ScheduleStatus.CONFIRMED),
    )

    val requestListItems = listOf(
        DRequestListItem("rli1", "srl1", "r2", 1),
        DRequestListItem("rli2", "srl2", "r7", 1),
    )
}
