package hector.ruiz.data.datasources

import hector.ruiz.commons.ResponseResult

interface NetworkDataSource {

    suspend fun getCharacters(): ResponseResult<Any>

    suspend fun getCharacter(characterId: Int): ResponseResult<Any>
}
