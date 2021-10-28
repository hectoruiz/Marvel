package hector.ruiz.marvel.di

import android.content.Context
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import hector.ruiz.datasource.api.ApiClient
import hector.ruiz.datasource.api.ApiService
import hector.ruiz.datasource.interceptors.MarvelInterceptor
import hector.ruiz.marvel.R
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApplicationModule {

    @Provides
    @Singleton
    fun providerMarvelInterceptor(@ApplicationContext context: Context): MarvelInterceptor {
        return MarvelInterceptor(
            context.getString(R.string.marvel_api_public_key),
            context.getString(R.string.marvel_api_private_key)
        )
    }

    @Provides
    @Singleton
    fun providerApiClient(marvelInterceptor: MarvelInterceptor): ApiClient {
        return ApiClient(marvelInterceptor)
    }

    @Provides
    @Singleton
    fun providerOKHttpClient(apiClient: ApiClient): OkHttpClient {
        return apiClient.okHttpClient
    }

    @Provides
    @Singleton
    fun providerRetrofit(apiClient: ApiClient): Retrofit {
        return apiClient.retrofit
    }

    @Provides
    @Singleton
    fun providerApiService(retrofit: Retrofit): ApiService {
        return retrofit.create()
    }

    @Provides
    @Singleton
    fun providerPicasso(
        @ApplicationContext context: Context,
        okHttpClient: OkHttpClient
    ): Picasso {
        return Picasso.Builder(context).downloader(OkHttp3Downloader(okHttpClient)).build()
    }
}
