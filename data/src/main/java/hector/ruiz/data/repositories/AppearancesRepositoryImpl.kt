package hector.ruiz.data.repositories

import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.usecase.repositories.AppearancesRepository
import javax.inject.Inject

class AppearancesRepositoryImpl @Inject constructor(private val networkDataSource: NetworkDataSource) :
    AppearancesRepository {

    override suspend fun getAppearances(url: String) =
        networkDataSource.getAppearances(url)
}