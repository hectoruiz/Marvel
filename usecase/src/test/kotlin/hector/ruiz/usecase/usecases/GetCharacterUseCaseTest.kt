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

class GetCharacterUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var characterRepository: CharacterRepository

    private val getCharacterUseCase by lazy {
        GetCharacterUseCase(characterRepository)
    }

    @Test
    fun `getCharacterUseCase request getCharacter`() {
        val responseData = mockk<ResponseResult<ResponseData>>()
        coEvery { characterRepository.getCharacter(CHARACTER_ID) } returns responseData

        val result = runBlocking { getCharacterUseCase(CHARACTER_ID) }

        assertEquals(responseData, result)
    }

    private companion object {
        const val CHARACTER_ID = 2
    }
}
