package hector.ruiz.datasource.datasources

import hector.ruiz.commons.ResponseResult
import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.datasource.api.ApiService
import hector.ruiz.domain.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Retrofit
import retrofit2.create
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(retrofit: Retrofit) : NetworkDataSource {

    private val service = retrofit.create<ApiService>()

    override suspend fun getCharacters(): ResponseResult<ResponseData> {
        return withContext(Dispatchers.IO) {
            service.getCharacters().let {
                if (it.isSuccessful) {
                    ResponseResult(null, it.body())
                } else {
                    ResponseResult(it.code(), null)
                }
            }
        }
    }

    override suspend fun getCharacter(characterId: Int): ResponseResult<ResponseData> {
        return withContext(Dispatchers.IO) {
            service.getCharacter(characterId).let {
                if (it.isSuccessful) {
                    ResponseResult(null, it.body())
                } else {
                    ResponseResult(it.code(), null)
                }
            }
        }
    }

    override suspend fun getAppearances(url: String): ResponseResult<ResponseData> {
        return withContext(Dispatchers.IO) {
            service.getAppearances(url).let {
                if (it.isSuccessful) {
                    ResponseResult(null, it.body())
                } else {
                    ResponseResult(it.code(), null)
                }
            }
        }
    }
}
