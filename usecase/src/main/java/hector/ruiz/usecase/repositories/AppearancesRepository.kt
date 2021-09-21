package hector.ruiz.usecase.repositories

import hector.ruiz.commons.ResponseResult
import hector.ruiz.domain.ResponseData

interface AppearancesRepository {

    suspend fun getAppearances(url: String): ResponseResult<ResponseData>
}
