package hector.ruiz.datasource.api

import hector.ruiz.datasource.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.math.BigInteger
import java.security.MessageDigest
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiClient @Inject constructor(private val publicKey: String, private val privateKey: String) {

    private val marvelInterceptor = Interceptor { chain ->
        val request = chain.request()
        val defaultUrl = request.url

        val url = defaultUrl.newBuilder()
            .addQueryParameter(TIMESTAMP, timeStamp)
            .addQueryParameter(API_KEY, publicKey)
            .addQueryParameter(HASH, getHash())
            .build()

        val requestBuilder = request.newBuilder().url(url)
        chain.proceed(requestBuilder.build())
    }

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    private val timeStamp = Date().time.toString()

    private fun getHash(): String {
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(
            1,
            md.digest((timeStamp + privateKey + publicKey).toByteArray())
        ).toString(16).padStart(32, '0')
    }

    private val okHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(marvelInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit.Builder = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())

    companion object {
        const val TIMESTAMP = "ts"
        const val API_KEY = "apikey"
        const val HASH = "hash"
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        const val TIMEOUT = 20L
    }
}
