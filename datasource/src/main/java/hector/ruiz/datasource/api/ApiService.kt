package hector.ruiz.datasource.api

import hector.ruiz.domain.ResponseData
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("characters")
    suspend fun getCharacters(): Response<ResponseData>

    @GET("characters/{$CHARACTER_ID}")
    suspend fun getCharacter(
        @Path(CHARACTER_ID) characterId: Int
    ): Response<ResponseData>

    private companion object {
        const val CHARACTER_ID = "characterId"
    }
}
