package hector.ruiz.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hector.ruiz.domain.Character
import hector.ruiz.usecase.usecases.GetAppearancesDetailUseCase
import hector.ruiz.usecase.usecases.GetCharacterUseCase
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val getCharacterUseCase: GetCharacterUseCase,
    private val appearancesDetailUseCase: GetAppearancesDetailUseCase
) :
    ViewModel() {

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
        manageError()
    }

    private val _isLoading: MutableLiveData<Boolean> = MutableLiveData()
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _character: MutableLiveData<Character?> = MutableLiveData()
    val character: LiveData<Character?>
        get() = _character

    private val _appearances: MutableLiveData<List<Character?>> = MutableLiveData()
    val appearances: LiveData<List<Character?>>
        get() = _appearances

    private val _errorRequest: MutableLiveData<Boolean> = MutableLiveData()
    val errorRequest: LiveData<Boolean>
        get() = _errorRequest

    fun getCharacterDetail(characterId: Int) =
        viewModelScope.launch(exceptionHandler) {
            _isLoading.postValue(true)
            val result = getCharacterUseCase(characterId)
            result.data?.charactersData?.characterList?.let {
                _character.postValue(it[0])
                _isLoading.postValue(false)
            } ?: manageError()
        }

    private fun manageError() {
        _isLoading.postValue(false)
        if (_character.value == null || _appearances.value.isNullOrEmpty()) {
            _errorRequest.postValue(true)
        }
    }

    fun getItemDetail(collectionUrl: String?) {
        collectionUrl?.let { url ->
            viewModelScope.launch(exceptionHandler) {
                _isLoading.postValue(true)
                val result =
                    appearancesDetailUseCase(url.replace(NON_ENCRYPTED_REQUEST, ENCRYPTED_REQUEST))
                result.data?.charactersData?.characterList?.let {
                    _appearances.postValue(it)
                    _isLoading.postValue(false)
                } ?: manageError()
            }
        }
    }

    private companion object {
        const val ENCRYPTED_REQUEST = "https"
        const val NON_ENCRYPTED_REQUEST = "http"
    }
}
