package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.CharacterRepository
import javax.inject.Inject

class GetCharacterUseCase @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke(characterId: Int) =
        characterRepository.getCharacter(characterId)
}