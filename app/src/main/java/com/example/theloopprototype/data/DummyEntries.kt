package com.example.theloopprototype.data

import com.example.theloopprototype.models.DVisitEntry
import java.time.LocalDateTime

object DummyVisitEntries {
    val visitEntries = listOf(
        DVisitEntry(
            id = "v1", petId = "p5", requestId = "r3", ahtId = "u6",
            visitDateTime = LocalDateTime.of(2026, 8, 5, 10, 15),
            reasonForVisit = "Routine deworming", outcome = "Treated on site",
            prescribedAction = "Deworming tablet administered; no follow-up needed",
            illnessFlag = false, illnessTypeId = null, illnessDescription = null,
            returnVisitFlag = false
        ),
        DVisitEntry(
            id = "v2", petId = "p1", requestId = null, ahtId = "u7",
            visitDateTime = LocalDateTime.of(2026, 8, 6, 13, 40),
            reasonForVisit = "Walk-in — visible skin irritation",
            outcome = "Mange confirmed, treatment started",
            prescribedAction = "Topical treatment applied; owner advised to return in 2 weeks",
            illnessFlag = true, illnessTypeId = "it1", illnessDescription = "Moderate mange on hindquarters",
            returnVisitFlag = true
        ),
    )
}
