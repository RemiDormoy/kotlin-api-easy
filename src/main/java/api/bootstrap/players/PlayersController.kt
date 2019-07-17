package api.bootstrap.players

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter

@RestController
class PlayersController {

    @Autowired
    lateinit var repository: PlayersRepository

    @GetMapping("/teams/{id}/players")
    fun getPlayers(@PathVariable("id") id: String) = repository.getPlayers(id).map {
        RestRessource(
            id = it.id,
            name = it.name,
            position = it.position,
            dateOfBirth = it.dateOfBirth.format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
            nationality = it.nationality
        )
    }

    data class RestRessource(
        val id: Int,
        val name: String,
        val position: String,
        val dateOfBirth: String,
        val nationality: String
    )
}

@ControllerAdvice(assignableTypes = [PlayersController::class])
class PlayersControllerAdvice {

    @ExceptionHandler(TeamNotFoundException::class)
    fun invalidParams(exc: TeamNotFoundException): ResponseEntity<ApiError> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
            .contentType(MediaType.APPLICATION_JSON)
            .body(ApiError("No team found for this id : ${exc.id}"))

    }
}

data class ApiError(
    val message: String
)