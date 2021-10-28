package hector.ruiz.data.repositories

import hector.ruiz.commons.ResponseResult
import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.domain.ResponseData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test

class CharacterRepositoryImplTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var networkDataSource: NetworkDataSource

    private val characterRepositoryImpl by lazy {
        CharacterRepositoryImpl(networkDataSource)
    }

    @Test
    fun `characterRepositoryImpl requesting getCharacters`() {
        val responseData = mockk<ResponseResult<ResponseData>>(relaxed = true)

        coEvery { networkDataSource.getCharacters(PAGE_NUMBER) } returns responseData
        val result = runBlocking {
            characterRepositoryImpl.getCharacters(PAGE_NUMBER)
        }

        assertEquals(responseData, result)
    }

    @Test
    fun `characterRepositoryImpl requesting getCharacter`() {
        val responseData = mockk<ResponseResult<ResponseData>>(relaxed = true)

        coEvery { networkDataSource.getCharacter(CHARACTER_ID) } returns responseData
        val result = runBlocking {
            characterRepositoryImpl.getCharacter(CHARACTER_ID)
        }

        assertEquals(responseData, result)
    }

    private companion object {
        const val PAGE_NUMBER = 2
        const val CHARACTER_ID = 4
    }
}