package hector.ruiz.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import dagger.hilt.android.lifecycle.HiltViewModel
import hector.ruiz.domain.Character
import hector.ruiz.usecase.usecases.GetCharactersUseCase
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    val characterList: LiveData<PagingData<Character>> = getPaginatedList()

    private fun getPaginatedList(): LiveData<PagingData<Character>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                PagingDataSourceImpl(getCharactersUseCase)
            }
        ).liveData.cachedIn(viewModelScope)
    }
}
