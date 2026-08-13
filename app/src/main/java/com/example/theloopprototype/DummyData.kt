package com.example.theloopprototype

import com.example.theloopprototype.data.*

object DummyData {
    val areas = DummyLookups.areas
    val animalTypes = DummyLookups.animalTypes
    val illnessTypes = DummyLookups.illnessTypes

    val users = DummyUsers.users
    val pets = DummyPets.pets
    val visitEntries = DummyVisitEntries.visitEntries

    val requests = DummyRequests.requests
    val scheduledRequestLists = DummyRequests.scheduledRequestLists
    val requestListItems = DummyRequests.requestListItems

    val notifications = DummyNotifications.notifications
//changed here
    fun getPetById(id: String) = pets.find { it.id == id }
    fun getRequestsForOwner(ownerId: String) = requests.filter { it.ownerId == ownerId }
    fun getPetsForOwner(ownerId: String) = pets.filter { it.ownerId == ownerId }
    fun getUserName(userId: String) = users.find { it.id == userId }?.firstName ?: "Unknown User"
    fun getRequestById(id: String) = requests.find { it.id == id }
    fun getVisitEntriesForPet(petId: String) = visitEntries.filter { it.petId == petId }
    fun getVisitEntryByRequestId(requestId: String) = visitEntries.find { it.requestId == requestId }
}
