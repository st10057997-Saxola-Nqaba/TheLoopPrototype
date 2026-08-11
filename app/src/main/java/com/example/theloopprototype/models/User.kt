package com.example.theloopprototype.models

data class DUser(
    val id: String,
    val firstName: String,
    val lastName: String,
    val physicalAddress: String?,
    val cellphoneNumber: String,
    val emailAddress: String?,
    val role: Role
)
