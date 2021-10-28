package hector.ruiz.marvel.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.data.repositories.AppearancesRepositoryImpl
import hector.ruiz.data.repositories.CharacterRepositoryImpl
import hector.ruiz.datasource.datasources.NetworkDataSourceImpl
import hector.ruiz.usecase.repositories.AppearancesRepository
import hector.ruiz.usecase.repositories.CharacterRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class NetworkModule {

    @Binds
    abstract fun bindsNetworkDataSource(networkDataSourceImpl: NetworkDataSourceImpl): NetworkDataSource

    @Binds
    abstract fun bindsCharacterRepository(characterRepositoryImpl: CharacterRepositoryImpl): CharacterRepository

    @Binds
    abstract fun bindsAppearancesRepository(appearancesRepositoryImpl: AppearancesRepositoryImpl): AppearancesRepository
}
