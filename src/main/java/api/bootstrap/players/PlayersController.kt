package api.bootstrap.players

import io.swagger.annotations.Api
import io.swagger.annotations.ApiModelProperty
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.time.format.DateTimeFormatter

@RestController
@Api(value = "Find players", description = "Find the list of players playing on your favorite team", tags = ["Players"])
class PlayersController {

    @Autowired
    lateinit var repository: PlayersRepository

    @ApiOperation(value = "Find players with team id", notes = "See description below for query rules")
    @GetMapping("/teams/{id}/players")
    fun getPlayers(
        @ApiParam(value = "Team id", example = "527", required = true)
        @PathVariable("id") id: String
    ) = repository.getPlayers(id).map {
        RestRessource(
            id = it.id,
            name = it.name,
            position = it.position,
            dateOfBirth = it.dateOfBirth.format(DateTimeFormatter.ofPattern("d MMMM yyyy")),
            nationality = it.nationality
        )
    }

    data class RestRessource(
        @ApiModelProperty(value = "Player id", example = "53")
        val id: Int,
        @ApiModelProperty(value = "Player id", example = "Cristiano Ronaldo")
        val name: String,
        @ApiModelProperty(value = "Player id", example = "Attacker")
        val position: String,
        @ApiModelProperty(value = "Player date of birth", example = "12 mai 1980")
        val dateOfBirth: String,
        @ApiModelProperty(value = "Player nationality", example = "France")
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