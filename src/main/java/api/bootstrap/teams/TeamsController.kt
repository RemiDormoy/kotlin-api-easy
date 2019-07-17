package api.bootstrap.teams

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class TeamsController {

    @Autowired
    lateinit var repository: TeamRepository

    @GetMapping("/teams")
    fun players() = repository.getTeams()
}
