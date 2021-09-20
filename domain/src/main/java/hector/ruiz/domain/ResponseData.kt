package hector.ruiz.domain

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ResponseData(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    @Json(name = "etag") val tag: String?,
    @Json(name = "data") val charactersData: Data?
)
