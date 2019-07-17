package api.bootstrap.teams

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.lang.RuntimeException

@Repository
class TeamRepository @Autowired constructor(private val restTemplate: RestTemplate) {

    fun getTeams(): List<Team> {
        try {
            val url = "http://api.football-data.org/v2/competitions/2015/teams"
            val headers = HttpHeaders().apply { set("X-Auth-Token", "1d6f36da87a84ef8aa34ea2d5118d187") }
            val responseBody = restTemplate.exchange(url, HttpMethod.GET, HttpEntity<Any>(headers), Response::class.java).body
                ?: throw TeamsNotFoundException()
            return responseBody.teams.map {
                Team(
                    id = it.id,
                    name = it.name,
                    color = it.clubColors,
                    yearOfFoundation = it.founded
                )
            }
        } catch (e: RestClientException) {
            throw TeamsNotFoundException(e)
        }
    }

    // Response object
    data class Response(
        val teams: List<ResponseTeam>
    )

    data class ResponseTeam(
        val id: Int,
        val name: String,
        val founded: Int,
        val clubColors: String
    )
}

class TeamsNotFoundException(cause: RestClientException? = null) : RuntimeException(cause)