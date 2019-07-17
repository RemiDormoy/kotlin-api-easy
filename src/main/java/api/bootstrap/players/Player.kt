package api.bootstrap.players

import java.time.LocalDate

data class Player(
    val id: Int,
    val name: String,
    val position: String,
    val dateOfBirth: LocalDate,
    val nationality: String
)