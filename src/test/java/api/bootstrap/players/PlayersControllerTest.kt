package api.bootstrap.players

import api.bootstrap.teams.removeIndentation
import com.nhaarman.mockitokotlin2.given
import org.junit.Before
import org.junit.Test

import org.junit.Assert.*
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import java.time.LocalDate

@RunWith(MockitoJUnitRunner::class)
class PlayersControllerTest {

    @Mock
    private lateinit var repository: PlayersRepository
    @InjectMocks
    private lateinit var controller: PlayersController

    private lateinit var mockMvc: MockMvc

    @Before
    fun setUp() {
        mockMvc = MockMvcBuilders
            .standaloneSetup(controller)
            .setControllerAdvice(PlayersControllerAdvice())
            .build()
    }

    @Test
    fun getPlayers() {
        // Given
        given(repository.getPlayers("123")).willReturn(listOf(
            Player(12, "Cristiano Ronaldo", "Attacker", LocalDate.parse("2019-02-01"), "Portugaise")
        ))

        // When
        //language=JSON
        val expectedResponse = """
            [
                {
                "id":12,
                "name":"Cristiano Ronaldo",
                "position":"Attacker",
                "dateOfBirth":"1 February 2019",
                "nationality":"Portugaise"
                }
            ]
        """.removeIndentation()
        mockMvc.perform(
            MockMvcRequestBuilders.get("/teams/123/players"))

            // Then
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andExpect(MockMvcResultMatchers.content().string(expectedResponse))
    }

    @Test
    fun `getPlayers should return 400 when team id not found`() {
        // Given
        given(repository.getPlayers("123")).willThrow(TeamNotFoundException("123"))

        // When
        mockMvc.perform(
            MockMvcRequestBuilders.get("/teams/123/players"))

            // Then
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
    }
}