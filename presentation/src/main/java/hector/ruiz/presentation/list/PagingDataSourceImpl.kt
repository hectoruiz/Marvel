package hector.ruiz.presentation.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import hector.ruiz.domain.Character
import hector.ruiz.usecase.usecases.GetCharactersUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class PagingDataSourceImpl @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    PagingSource<Int, Character>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        return withContext(Dispatchers.IO) {
            val nextPageNumber = params.key ?: DEFAULT_PAGE
            try {
                getCharactersUseCase(nextPageNumber).let { responseResult ->
                    val responseList: List<Character>? =
                        responseResult.data?.charactersData?.characterList?.mapNotNull {
                            it
                        }
                    LoadResult.Page(
                        data = responseList ?: emptyList(),
                        prevKey = null,
                        nextKey = if (responseList.isNullOrEmpty()) {
                            null
                        } else {
                            responseResult.data?.charactersData?.run {
                                offset?.plus(limit ?: DEFAULT_PAGE_SIZE)
                            }
                        }
                    )
                }
            } catch (e: Exception) {
                LoadResult.Error(e)
            }
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition
    }

    private companion object {
        const val DEFAULT_PAGE = 0
        const val DEFAULT_PAGE_SIZE = 20
    }
}
