package api.bootstrap.teams

import com.nhaarman.mockitokotlin2.given
import org.assertj.core.api.Assertions
import org.junit.Test

import org.junit.Before
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class FindAndSortTeamsUseCaseTest {

    @Mock
    private lateinit var repository: TeamRepository
    @InjectMocks
    private lateinit var useCase: FindAndSortTeamsUseCase

    @Before
    fun setUp() {
        given(repository.getTeams()).willReturn(
            listOf(
                Team(1, "B Team ", "Blue", 2018),
                Team(3, "A Team", "Blue", 2019),
                Team(2, "C Team", "Blue", 2016)
            )
        )
    }

    @Test
    fun `execute should return teams sorted by id as default`() {
        // When
        val result = useCase.execute(null)

        // Then
        Assertions.assertThat(result).isEqualTo(
            listOf(
                Team(1, "B Team ", "Blue", 2018),
                Team(2, "C Team", "Blue", 2016),
                Team(3, "A Team", "Blue", 2019)
            )
        )
    }

    @Test
    fun `execute should return teams sorted by name when specified`() {
        // When
        val result = useCase.execute("name")

        // Then
        Assertions.assertThat(result).isEqualTo(
            listOf(
                Team(3, "A Team", "Blue", 2019),
                Team(1, "B Team ", "Blue", 2018),
                Team(2, "C Team", "Blue", 2016)
            )
        )
    }

    @Test
    fun `execute should return teams sorted by year of foundation when specified`() {
        // When
        val result = useCase.execute("foundation")

        // Then
        Assertions.assertThat(result).isEqualTo(
            listOf(
                Team(2, "C Team", "Blue", 2016),
                Team(1, "B Team ", "Blue", 2018),
                Team(3, "A Team", "Blue", 2019)
            )
        )
    }
}