package hector.ruiz.usecase.repositories

import hector.ruiz.commons.ResponseResult
import hector.ruiz.domain.ResponseData

interface CharacterRepository {

    suspend fun getCharacters(): ResponseResult<ResponseData>

    suspend fun getCharacter(characterId: Int): ResponseResult<ResponseData>
}
