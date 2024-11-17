package com.example.booknest

data class Hotel(
    val name: String = "",
    val description: String = "",
    val amenities: List<String> = listOf(),
    val rooms: Map<String, Room> = mapOf()
)

