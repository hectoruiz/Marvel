package hector.ruiz.data.datasources

import hector.ruiz.commons.ResponseResult
import hector.ruiz.domain.ResponseData

interface NetworkDataSource {

    suspend fun getCharacters(pageNumber: Int): ResponseResult<ResponseData>

    suspend fun getCharacter(characterId: Int): ResponseResult<ResponseData>

    suspend fun getAppearances(url: String): ResponseResult<ResponseData>
}
