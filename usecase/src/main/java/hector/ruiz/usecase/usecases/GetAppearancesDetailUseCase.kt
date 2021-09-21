package hector.ruiz.usecase.usecases

import hector.ruiz.usecase.repositories.AppearancesRepository
import javax.inject.Inject

class GetAppearancesDetailUseCase @Inject constructor(private val appearancesRepository: AppearancesRepository) {

    suspend operator fun invoke(url: String) =
        appearancesRepository.getAppearances(url)
}
