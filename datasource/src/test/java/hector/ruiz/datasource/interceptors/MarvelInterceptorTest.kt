package hector.ruiz.datasource.interceptors

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verifySequence
import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import org.junit.Before
import org.junit.Test

class MarvelInterceptorTest {

    init {
        MockKAnnotations.init(this)
    }

    @MockK
    private lateinit var chain: Interceptor.Chain

    @MockK
    private lateinit var request: Request

    @MockK
    private lateinit var httpUrl: HttpUrl

    @MockK
    private lateinit var httpUrlBuilder: HttpUrl.Builder

    @MockK
    private lateinit var requestBuilder : Request.Builder

    @MockK
    private lateinit var response : Response

    private val marvelInterceptor by lazy {
        MarvelInterceptor(PUBLIC_KEY, PRIVATE_KEY)
    }

    @Before
    fun setUp() {
        every { chain.request() } returns request
        every { request.url } returns httpUrl
        every { httpUrl.newBuilder() } returns httpUrlBuilder
        every { httpUrlBuilder.addQueryParameter(any(), any()) } returns httpUrlBuilder
        every { httpUrlBuilder.build() } returns httpUrl
        every { request.newBuilder() } returns requestBuilder
        every { requestBuilder.url(any<HttpUrl>()) } returns requestBuilder
        every { requestBuilder.build() } returns request
        every { chain.proceed(any()) } returns response
    }

    @Test
    fun `the interceptor add headers correctly`() {
        marvelInterceptor.intercept(chain)

        verifySequence {
            httpUrlBuilder.addQueryParameter("ts", any())
            httpUrlBuilder.addQueryParameter("apikey", PUBLIC_KEY)
            httpUrlBuilder.addQueryParameter("hash", any())
            httpUrlBuilder.build()
        }
    }

    private companion object {
        const val PUBLIC_KEY = "public_key"
        const val PRIVATE_KEY = "private_key"
    }
}
