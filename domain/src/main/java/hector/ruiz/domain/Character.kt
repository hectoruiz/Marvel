package hector.ruiz.domain

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Character(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val thumbnail: Thumbnail?,
    val resourceURI: String?,
    val comics: Detail?,
    val series: Detail?,
    val stories: Detail?,
    val events: Detail?,
    val urls: List<Urls?>?
)
