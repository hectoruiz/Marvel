package hector.ruiz.datasource.api

import hector.ruiz.datasource.BuildConfig
import hector.ruiz.datasource.interceptors.MarvelInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ApiClient @Inject constructor(marvelInterceptor: MarvelInterceptor) {

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        setLevel(
            if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        )
    }

    val okHttpClient = OkHttpClient().newBuilder()
        .addNetworkInterceptor(marvelInterceptor)
        .addInterceptor(loggingInterceptor)
        .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
        .readTimeout(TIMEOUT, TimeUnit.SECONDS)
        .build()

    val retrofit: Retrofit.Builder = Retrofit.Builder()
        .client(okHttpClient)
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())

    private companion object {
        const val BASE_URL = "https://gateway.marvel.com/v1/public/"
        const val TIMEOUT = 20L
    }
}
