package hector.ruiz.marvel.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.data.repositories.CharacterRepositoryImpl
import hector.ruiz.datasource.datasources.NetworkDataSourceImpl
import hector.ruiz.usecase.repositories.CharacterRepository
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    fun providerNetworkDataSource(retrofit: Retrofit): NetworkDataSource {
        return NetworkDataSourceImpl(retrofit)
    }

    @Provides
    fun providerCharacterRepository(networkDataSource: NetworkDataSource): CharacterRepository {
        return CharacterRepositoryImpl(networkDataSource)
    }
}
