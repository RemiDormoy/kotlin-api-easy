package api.bootstrap.teams

import org.assertj.core.api.Assertions
import org.junit.Test

import org.junit.Before
import org.springframework.http.MediaType
import org.springframework.test.web.client.MockRestServiceServer
import org.springframework.test.web.client.match.MockRestRequestMatchers
import org.springframework.test.web.client.response.MockRestResponseCreators
import org.springframework.web.client.RestTemplate
import org.springframework.web.util.UriComponentsBuilder

class TeamRepositoryTest {

    private lateinit var server: MockRestServiceServer
    private lateinit var repository: TeamRepository

    @Before
    fun setUp() {
        val restTemplate = RestTemplate()
        server = MockRestServiceServer.bindTo(restTemplate).build()
        repository = TeamRepository(restTemplate)
    }

    @Test
    fun `repository should serialize request when getTeams on repository is called`() {
        // Given
        val requestUri = UriComponentsBuilder.fromUriString("http://api.football-data.org/v2/competitions/2015/teams").build().encode().toString()
        server.expect(MockRestRequestMatchers.requestTo(requestUri)).andRespond(MockRestResponseCreators.withSuccess("successful response", MediaType.APPLICATION_JSON))

        // When
        try {
            repository.getTeams()
        } catch (xrs: Throwable) { /*we just check request call*/
        }

        // Then
        server.verify()
    }

    @Test
    fun `repository should deserialize request and return teams`() {
        // Given
        //language=JSON
        val responseBody = """
          {
            "count": 20,
            "filters": {},
            "competition": {
                "id": 2015,
                "area": {
                    "id": 2081,
                    "name": "France"
                },
                "name": "Ligue 1",
                "code": "FL1",
                "plan": "TIER_ONE",
                "lastUpdated": "2019-06-03T09:55:00Z"
            },
            "season": {
                "id": 499,
                "startDate": "2019-08-10",
                "endDate": "2020-05-31",
                "currentMatchday": 1,
                "winner": null
            },
            "teams": [
                {
                    "id": 512,
                    "area": {
                        "id": 2081,
                        "name": "France"
                    },
                    "name": "Stade Brestois 29",
                    "shortName": "Brest",
                    "tla": "SBR",
                    "crestUrl": "http://upload.wikimedia.org/wikipedia/de/c/cb/Stade_Brestois_29.svg",
                    "address": "Port de Plaisance, 470 bis rue Alain Colas Brest 29200",
                    "phone": "+33 (0298) 022030",
                    "website": "http://www.stade-brestois.com",
                    "email": "daniel.leroux.sb29@hotmail.fr",
                    "founded": 1903,
                    "clubColors": "White / Red",
                    "venue": "Stade Francis-Le Blé",
                    "lastUpdated": "2019-07-11T02:35:34Z"
                },
                {
                    "id": 516,
                    "area": {
                        "id": 2081,
                        "name": "France"
                    },
                    "name": "Olympique de Marseille",
                    "shortName": "Marseille",
                    "tla": "OM",
                    "crestUrl": "https://upload.wikimedia.org/wikipedia/fr/thumb/4/43/Logo_Olympique_de_Marseille.svg/130px-Logo_Olympique_de_Marseille.svg.png",
                    "address": "La Commanderie, 33, traverse de La Martine Marseille 13012",
                    "phone": "+33 (049) 1765609",
                    "website": "http://www.om.net",
                    "email": "om@olympiquedemarseille.com",
                    "founded": 1898,
                    "clubColors": "White / Blue",
                    "venue": "Orange Vélodrome",
                    "lastUpdated": "2019-07-11T02:35:35Z"
                },
                {
                    "id": 527,
                    "area": {
                        "id": 2081,
                        "name": "France"
                    },
                    "name": "AS Saint-Étienne",
                    "shortName": "Saint-Étienne",
                    "tla": "STE",
                    "crestUrl": "https://upload.wikimedia.org/wikipedia/commons/thumb/2/2c/Logo_AS_Saint-%C3%89tienne.svg/130px-Logo_AS_Saint-%C3%89tienne.svg.png",
                    "address": "11 rue de Verdun L'Étrat 42580",
                    "phone": "+33 (0477) 929899",
                    "website": "http://www.asse.fr",
                    "email": "asse@lrafoot.org",
                    "founded": 1919,
                    "clubColors": "Green / White",
                    "venue": "Stade Geoffroy-Guichard",
                    "lastUpdated": "2019-07-11T02:40:01Z"
                }
            ]
        }
        """.trimIndent()
        server.expect { }.andRespond(MockRestResponseCreators.withSuccess(responseBody, MediaType.APPLICATION_JSON))

        // When
        val result = repository.getTeams()

        // Then
        Assertions.assertThat(result).isEqualTo(listOf(
            Team(id=512, name="Stade Brestois 29", color="White / Red", yearOfFoundation=1903),
            Team(id=516, name="Olympique de Marseille", color="White / Blue", yearOfFoundation=1898),
            Team(id=527, name="AS Saint-Étienne", color="Green / White", yearOfFoundation=1919)
        ))
    }
}

fun String.removeIndentation() = this.split("\n").joinToString("") { it.trim() }