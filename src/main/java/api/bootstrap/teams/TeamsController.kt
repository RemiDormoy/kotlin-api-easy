package api.bootstrap.teams

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
class TeamsController(private val useCase: FindAndSortTeamsUseCase) {

    @GetMapping("/teams")
    fun players(@RequestParam(value = "sort", required = false) sort: String?) = useCase.execute(sort)
}

