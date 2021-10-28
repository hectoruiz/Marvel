package hector.ruiz.datasource.datasources

import hector.ruiz.commons.ResponseResult
import hector.ruiz.data.datasources.NetworkDataSource
import hector.ruiz.datasource.api.ApiService
import hector.ruiz.domain.ResponseData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkDataSourceImpl @Inject constructor(private val apiService: ApiService) :
    NetworkDataSource {

    override suspend fun getCharacters(pageNumber: Int): ResponseResult<ResponseData> {
        return withContext(Dispatchers.IO) {
            apiService.getCharacters(pageNumber).let {
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
            apiService.getCharacter(characterId).let {
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
            apiService.getAppearances(url).let {
                if (it.isSuccessful) {
                    ResponseResult(null, it.body())
                } else {
                    ResponseResult(it.code(), null)
                }
            }
        }
    }
}
