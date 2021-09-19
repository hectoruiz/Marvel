package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.CharacterRepository
import javax.inject.Inject

class GetCharacters @Inject constructor(private val characterRepository: CharacterRepository) {

    suspend operator fun invoke() =
        characterRepository.getCharacters()
}
