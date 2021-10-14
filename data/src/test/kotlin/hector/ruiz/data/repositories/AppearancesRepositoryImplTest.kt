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

class AppearancesRepositoryImplTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var networkDataSource: NetworkDataSource

    private val appearancesRepositoryImpl by lazy {
        AppearancesRepositoryImpl(networkDataSource)
    }

    @Test
    fun `appearancesRepositoryImpl requesting getAppearances`() {
        val responseData = mockk<ResponseResult<ResponseData>>(relaxed = true)

        coEvery { networkDataSource.getAppearances(APPEARANCE_URL) } returns responseData
        val result = runBlocking {
            appearancesRepositoryImpl.getAppearances(APPEARANCE_URL)
        }

        assertEquals(responseData, result)
    }

    private companion object {
        const val APPEARANCE_URL = "http://blablabla.com"
    }
}
