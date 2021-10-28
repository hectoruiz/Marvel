package hector.ruiz.data.repositories

import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.usecase.repositories.CharacterRepository
import javax.inject.Inject

class CharacterRepositoryImpl @Inject constructor(private val networkDataSource: NetworkDataSource) :
    CharacterRepository {

    override suspend fun getCharacters(pageNumber: Int) =
        networkDataSource.getCharacters(pageNumber)

    override suspend fun getCharacter(characterId: Int) =
        networkDataSource.getCharacter(characterId)
}
