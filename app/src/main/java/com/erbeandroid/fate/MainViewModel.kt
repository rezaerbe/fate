package com.erbeandroid.fate

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erbeandroid.fate.core.data.model.Event
import com.erbeandroid.fate.core.data.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository
) : ViewModel() {

/*    private val _servantState = MutableStateFlow<StateData<List<Servant>>>(StateData.Loading)
    val servantState: StateFlow<StateData<List<Servant>>> = _servantState*/

    private val _eventState = MutableStateFlow<StateData<Event?>>(StateData.Loading)
    val eventState: StateFlow<StateData<Event?>> = _eventState

    init {
/*        viewModelScope.launch {
            try {
                repository.getServants()
                    .collect { servants ->
                        _servantState.value = StateData.Success(servants)
                    }
            } catch (exception: Exception) {
                _servantState.value = StateData.Error(exception)
            }
        }*/

        viewModelScope.launch {
            try {
                repository.getEvents()
                    .collect { event ->
                        _eventState.value = StateData.Success(event)
                    }
            } catch (exception: Exception) {
                _eventState.value = StateData.Error(exception)
            }
        }
    }
}

sealed interface StateData<out T> {
    data class Success<T>(val data: T) : StateData<T>
    data class Error(val exception: Exception) : StateData<Nothing>
    object Loading : StateData<Nothing>
}