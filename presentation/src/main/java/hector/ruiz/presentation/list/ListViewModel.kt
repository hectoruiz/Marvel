package hector.ruiz.presentation.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hector.ruiz.domain.Character
import hector.ruiz.usecase.usecases.GetCharactersUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListViewModel @Inject constructor(private val getCharactersUseCase: GetCharactersUseCase) :
    ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        manageError()
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _characterList: MutableLiveData<List<Character?>> = MutableLiveData()
    val characterList: LiveData<List<Character?>>
        get() = _characterList

    private val _errorRequest: MutableLiveData<Boolean> = MutableLiveData()
    val errorRequest: LiveData<Boolean>
        get() = _errorRequest

    fun getCharacterList() =
        viewModelScope.launch(exceptionHandler) {
            _isLoading.postValue(true)
            val result = getCharactersUseCase()
            result.data?.charactersData?.characterList?.let {
                _characterList.postValue(it)
                _isLoading.postValue(false)
            } ?: manageError()
        }

    private fun manageError() {
        _isLoading.postValue(false)
        if (_characterList.value.isNullOrEmpty()) {
            _errorRequest.postValue(true)
        }
    }
}
