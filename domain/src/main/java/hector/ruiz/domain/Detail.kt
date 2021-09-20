package hector.ruiz.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Detail(
    val available: Int?,
    val collectionURI: String?,
    val items: List<Items?>?,
    val returned: Int?
)
