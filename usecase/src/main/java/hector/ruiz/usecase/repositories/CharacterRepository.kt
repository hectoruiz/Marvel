package hector.ruiz.usecase.repositories

import hector.ruiz.commons.ResponseResult

interface CharacterRepository {

    suspend fun getCharacters(): ResponseResult<Any>

    suspend fun getCharacter(characterId: Int): ResponseResult<Any>
}
