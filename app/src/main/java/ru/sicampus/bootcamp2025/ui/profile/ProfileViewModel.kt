package ru.sicampus.bootcamp2025.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.data.profile.ProfileNetworkDataSource
import ru.sicampus.bootcamp2025.data.profile.ProfileRepoImpl
import ru.sicampus.bootcamp2025.domain.profile.GetProfileUseCase

class ProfileViewModel (
    private val getProfileUseCase: GetProfileUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()



    init {
        updateState()
    }
    fun clickRefresh(){
        updateState()
    }

    private fun updateState(){
        viewModelScope.launch {
            _state.emit(State.Loading)
            _state.emit(
                getProfileUseCase.invoke().fold(
                    onSuccess = {
                        data -> State.Show(data)

                    },
                    onFailure = { _ -> State.Error("Error")
                    }
                )
            )
        }
    }

    sealed interface State{
        data object Loading : State
        data class Show(
            val user: UserDTO
        ) : State
        data class Error(
            val text: String
        ) : State

    }
    companion object{
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            @Suppress("UNCHECKED_CAST")
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return ProfileViewModel(
                    getProfileUseCase = GetProfileUseCase(
                        repo = ProfileRepoImpl(
                            profileNetworkDataSource = ProfileNetworkDataSource()
                        )
                    )
                ) as T
            }
        }
    }
}