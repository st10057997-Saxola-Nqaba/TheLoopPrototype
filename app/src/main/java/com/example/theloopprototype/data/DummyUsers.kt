package com.example.theloopprototype.data

import com.example.theloopprototype.models.DUser
import com.example.theloopprototype.models.Role

object DummyUsers {
    val users = listOf(
        DUser("u1", "Nomvula", "Dlamini", "12 Setai St, Tembisa", "0821234567", "nomvula.d@example.com", Role.OWNER),
        DUser("u2", "Sipho", "Khumalo", "45 Winnie Mandela Dr, Ivory Park", "0827654321", null, Role.OWNER),
        DUser("u3", "Grace", "Mokoena", "8 Extension 4, Diepsloot", "0731122334", "grace.mokoena@example.com", Role.OWNER),
        DUser("u4", "Thabo", "Nkosi", "21 Rabie St, Olievenhoutbosch", "0839988776", null, Role.OWNER),
        DUser("u5", "Lindiwe", "Zulu", "3 Hospital View, Tembisa", "0715566778", "lindiwe.zulu@example.com", Role.OWNER),
        DUser("u6", "Kagiso", "Molefe", null, "0724433221", "kagiso.molefe@animalsindistress.org.za", Role.AHT),
        DUser("u7", "Priya", "Naidoo", null, "0765544332", "priya.naidoo@animalsindistress.org.za", Role.AHT),
        DUser("u8", "Sarah", "van Wyk", null, "0119876543", "sarah.vanwyk@animalsindistress.org.za", Role.ADMIN),
    )
}
