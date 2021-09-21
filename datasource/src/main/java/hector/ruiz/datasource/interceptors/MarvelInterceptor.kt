package hector.ruiz.datasource.interceptors

import okhttp3.Interceptor
import okhttp3.Response
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import javax.inject.Inject

class MarvelInterceptor @Inject constructor(
    private val publicKey: String,
    private val privateKey: String
) : Interceptor {

    private val timeStamp = Date().time.toString()

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val defaultUrl = request.url

        val url = defaultUrl.newBuilder()
            .addQueryParameter(TIMESTAMP, timeStamp)
            .addQueryParameter(API_KEY, publicKey)
            .addQueryParameter(HASH, getHash())
            .build()

        val requestBuilder = request.newBuilder().url(url)
        return chain.proceed(requestBuilder.build())
    }

    private fun getHash(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(
            1,
            md.digest((timeStamp + privateKey + publicKey).toByteArray())
        ).toString(16).padStart(32, '0')
    }

    private companion object {
        const val TIMESTAMP = "ts"
        const val API_KEY = "apikey"
        const val HASH = "hash"
    }
}
