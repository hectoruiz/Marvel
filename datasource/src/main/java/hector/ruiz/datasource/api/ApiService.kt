package hector.ruiz.datasource.api

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(): Response<Any>

    @GET("characters/{characterId}")
    suspend fun getCharacter(
        @Path(CHARACTER_ID) characterId: Int): Response<Any>

    private companion object {
        const val CHARACTER_ID = "characterId"
    }
}
