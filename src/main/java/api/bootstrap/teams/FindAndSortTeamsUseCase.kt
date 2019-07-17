package api.bootstrap.teams

import org.springframework.stereotype.Component

@Component
class FindAndSortTeamsUseCase(private val repository: TeamRepository) {

    fun execute(sort: String?): List<Team> {
        val teams = repository.getTeams()
        return when (sort) {
            "foundation" -> teams.sortedBy { it.yearOfFoundation }
            "name" -> teams.sortedBy { it.name }
            else -> teams.sortedBy { it.id }
        }
    }
}