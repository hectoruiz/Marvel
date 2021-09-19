package hector.ruiz.datasource.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject

class ApiClient @Inject constructor() {

    val retrofit: Retrofit.Builder = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())

    companion object {
        const val BASE_URL = "BASE_URL"
    }
}
