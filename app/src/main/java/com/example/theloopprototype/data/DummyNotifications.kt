package com.example.theloopprototype.data

import com.example.theloopprototype.models.DNotification
import com.example.theloopprototype.models.NotificationType
import java.time.LocalDateTime

object DummyNotifications {
    val notifications = listOf(
        DNotification("n1", "area2", "u8",
            "Our mobile clinic will be visiting Tembisa on 18 August. Come find us for check-ups and treatment!",
            NotificationType.OUTREACH, LocalDateTime.of(2026, 8, 11, 9, 0)),
        DNotification("n2", "area3", "u8",
            "Mobile clinic outreach confirmed for Ivory Park on 17 August.",
            NotificationType.OUTREACH, LocalDateTime.of(2026, 8, 11, 9, 5)),
        DNotification("n3", null, "u8",
            "Reminder: SAID's Vorna Valley hospital is open for emergencies every day, even outside outreach schedules.",
            NotificationType.OUTREACH, LocalDateTime.of(2026, 8, 1, 8, 0)),
    )
}
