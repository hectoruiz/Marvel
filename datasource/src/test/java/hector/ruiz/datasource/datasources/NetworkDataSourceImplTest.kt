package hector.ruiz.datasource.datasources

import hector.ruiz.datasource.api.ApiClient
import hector.ruiz.datasource.api.ApiService
import hector.ruiz.domain.ResponseData
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Before
import org.junit.Test
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.create

class NetworkDataSourceImplTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var apiClient: ApiClient

    @MockK
    private lateinit var retrofit: Retrofit

    @MockK
    private lateinit var apiService: ApiService

    private val networkDataSourceImpl by lazy {
        NetworkDataSourceImpl(retrofit)
    }

    @Before
    fun setUp() {
        every { apiClient.retrofit } returns retrofit
        every { retrofit.create<ApiService>() } returns apiService
    }

    @Test
    fun `error requesting getCharacters`() {
        coEvery { apiService.getCharacters() } returns Response.error(
            ERROR_CODE,
            mockk(relaxed = true)
        )
        val result = runBlocking {
            networkDataSourceImpl.getCharacters()
        }

        assertEquals(ERROR_CODE, result.errorCode)
        assertNull(result.data)
    }

    @Test
    fun `success requesting getCharacters`() {
        val responseData = mockk<ResponseData>()
        coEvery { apiService.getCharacters() } returns Response.success(SUCCESS_CODE, responseData)
        val result = runBlocking {
            networkDataSourceImpl.getCharacters()
        }

        assertNull(result.errorCode)
        assertEquals(responseData, result.data)
    }

    @Test
    fun `error requesting getCharacter`() {
        coEvery { apiService.getCharacter(CHARACTER_ID) } returns Response.error(
            ERROR_CODE,
            mockk(relaxed = true)
        )
        val result = runBlocking {
            networkDataSourceImpl.getCharacter(CHARACTER_ID)
        }

        assertEquals(ERROR_CODE, result.errorCode)
        assertNull(result.data)
    }

    @Test
    fun `success requesting getCharacter`() {
        val responseData = mockk<ResponseData>()
        coEvery { apiService.getCharacter(CHARACTER_ID) } returns Response.success(
            SUCCESS_CODE,
            responseData
        )
        val result = runBlocking {
            networkDataSourceImpl.getCharacter(CHARACTER_ID)
        }

        assertNull(result.errorCode)
        assertEquals(responseData, result.data)
    }

    @Test
    fun `error requesting getAppearances`() {
        coEvery { apiService.getAppearances(APPEARANCE_URL) } returns Response.error(
            ERROR_CODE,
            mockk(relaxed = true)
        )
        val result = runBlocking {
            networkDataSourceImpl.getAppearances(APPEARANCE_URL)
        }

        assertEquals(ERROR_CODE, result.errorCode)
        assertNull(result.data)
    }

    @Test
    fun `success requesting getAppearances`() {
        val responseData = mockk<ResponseData>()
        coEvery { apiService.getAppearances(APPEARANCE_URL) } returns Response.success(
            SUCCESS_CODE,
            responseData
        )
        val result = runBlocking {
            networkDataSourceImpl.getAppearances(APPEARANCE_URL)
        }

        assertNull(result.errorCode)
        assertEquals(responseData, result.data)
    }

    private companion object {
        const val ERROR_CODE = 400
        const val SUCCESS_CODE = 200
        const val CHARACTER_ID = 12
        const val APPEARANCE_URL = "http://blablabla.com"
    }
}
