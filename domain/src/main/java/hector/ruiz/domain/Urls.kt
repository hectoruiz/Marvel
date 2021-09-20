package hector.ruiz.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Urls(
    val type: String?,
    val url: String?
)
