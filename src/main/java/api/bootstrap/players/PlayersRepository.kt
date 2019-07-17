package api.bootstrap.players

import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.stereotype.Repository
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate
import java.lang.RuntimeException
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Repository
class PlayersRepository(private val restTemplate: RestTemplate) {

    fun getPlayers(id: String): List<Player> {
        try {
            val url = "http://api.football-data.org/v2/teams/$id"
            val headers = HttpHeaders().apply { set("X-Auth-Token", "1d6f36da87a84ef8aa34ea2d5118d187") }
            val responseBody =
                restTemplate.exchange(url,
                    HttpMethod.GET,
                    HttpEntity<Any>(headers), Response::class.java).body
                    ?: throw TeamNotFoundException(id)
            return responseBody.squad.map {
                Player(
                    name = it.name,
                    id = it.id,
                    dateOfBirth = LocalDate.parse(
                        it.dateOfBirth,
                        DateTimeFormatter.ISO_DATE_TIME
                    ),
                    nationality = it.nationality,
                    position = it.position ?: it.role
                )
            }
        } catch (e: RestClientException) {
            throw TeamNotFoundException(id)
        }
    }

    // Response object
    data class Response(
        val squad: List<Squad>
    )

    data class Squad(
        val id: Int,
        val name: String,
        val position: String?,
        val dateOfBirth: String,
        val nationality: String,
        val role: String
    )
}

class TeamNotFoundException(val id: String) : RuntimeException()
