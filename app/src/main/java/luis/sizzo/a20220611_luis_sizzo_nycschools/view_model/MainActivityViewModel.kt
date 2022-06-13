package luis.sizzo.a20220611_luis_sizzo_nycschools.view_model

import androidx.lifecycle.*
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.UI_State
import luis.sizzo.a20220611_luis_sizzo_nycschools.model.res.Repository
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val repository: Repository,
    private val coroutineScope: CoroutineScope,
) : ViewModel() {

    private val _schoolResponse = MutableLiveData<UI_State>()
    val getSchoolResponse: MutableLiveData<UI_State>
        get() = _schoolResponse

    private val _satResponse = MutableLiveData<UI_State>()
    val getSatResponse: MutableLiveData<UI_State>
        get() = _satResponse

    private val _stateView = MutableLiveData<Boolean>()
    val stateView: MutableLiveData<Boolean>
        get() = _stateView

    init {
        getSchools()
    }

    fun getSchools() {
        coroutineScope.launch {
            repository.getSchools().collect { state_result ->
                _schoolResponse.postValue(state_result)
            }
        }
    }

    fun getStateView(state: Boolean) {
        _stateView.value = state
    }

    fun getSat(dbn: String) {
        coroutineScope.launch {
            repository.getSat(dbn).collect {
                _satResponse.postValue(it)
            }
        }
    }
}