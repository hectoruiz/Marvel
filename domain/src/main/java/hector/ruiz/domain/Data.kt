package hector.ruiz.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Data(
    val offset: Int?,
    val limit: Int?,
    val total: Int?,
    val count: Int?,
    @Json(name = "data") val characterList: List<Character?>?
)
