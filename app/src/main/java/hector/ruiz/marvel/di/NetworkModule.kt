package hector.ruiz.marvel.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.data.repositories.CharacterRepositoryImpl
import hector.ruiz.datasource.api.ApiClient
import hector.ruiz.datasource.datasources.NetworkDataSourceImpl
import hector.ruiz.marvel.R
import hector.ruiz.usecase.repositories.CharacterRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun providerApiClient(@ApplicationContext context: Context): ApiClient {
        return ApiClient(
            context.getString(R.string.marvel_api_public_key),
            context.getString(R.string.marvel_api_private_key)
        )
    }

    @Provides
    @Singleton
    fun providerRetrofit(apiClient: ApiClient): Retrofit {
        return apiClient.retrofit.build()
    }

    @Provides
    fun providerNetworkDataSource(retrofit: Retrofit): NetworkDataSource {
        return NetworkDataSourceImpl(retrofit)
    }

    @Provides
    fun providerVehicleRepository(networkDataSource: NetworkDataSource): CharacterRepository {
        return CharacterRepositoryImpl(networkDataSource)
    }
}
