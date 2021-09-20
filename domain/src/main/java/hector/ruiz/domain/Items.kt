package hector.ruiz.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Items(
    val resourceURI: String?,
    val name: String?,
    val type: String?
)
