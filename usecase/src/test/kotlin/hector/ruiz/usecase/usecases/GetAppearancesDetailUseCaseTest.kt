package hector.ruiz.usecase.usecases

import hector.ruiz.commons.ResponseResult
import hector.ruiz.domain.ResponseData
import hector.ruiz.usecase.repositories.AppearancesRepository
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class GetAppearancesDetailUseCaseTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var appearancesRepository: AppearancesRepository

    private val getAppearancesDetailUseCase by lazy {
        GetAppearancesDetailUseCase(appearancesRepository)
    }

    @Test
    fun `getCharactersUseCase request getCharacters`() {
        val responseData = mockk<ResponseResult<ResponseData>>()
        coEvery { appearancesRepository.getAppearances(APPEARANCE_URL) } returns responseData

        val result = runBlocking { getAppearancesDetailUseCase(APPEARANCE_URL) }

        assertEquals(responseData, result)
    }

    private companion object {
        const val APPEARANCE_URL = "http://blablabla.com"
    }
}
