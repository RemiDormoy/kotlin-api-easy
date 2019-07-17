package api.bootstrap.players

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
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