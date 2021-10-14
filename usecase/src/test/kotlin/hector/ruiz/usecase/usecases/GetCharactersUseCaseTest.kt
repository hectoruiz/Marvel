package hector.ruiz.usecase.usecases

import hector.ruiz.commons.ResponseResult
import hector.ruiz.domain.ResponseData
import hector.ruiz.usecase.repositories.CharacterRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetCharactersUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var characterRepository: CharacterRepository

    private val getCharactersUseCase by lazy {
        GetCharactersUseCase(characterRepository)
    }

    @Test
    fun `getCharactersUseCase request getCharacters`() {
        val responseData = mockk<ResponseResult<ResponseData>>()
        coEvery { characterRepository.getCharacters() } returns responseData

        val result = runBlocking { getCharactersUseCase() }

        assertEquals(responseData, result)
    }
}
