package ru.sicampus.bootcamp2025.ui.profile.view

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import ru.sicampus.bootcamp2025.data.profile.ViewProfileNetworkDataSource
import ru.sicampus.bootcamp2025.data.profile.ProfileRepoImpl
import ru.sicampus.bootcamp2025.domain.entities.UserEntity
import ru.sicampus.bootcamp2025.domain.profile.ViewProfileUseCase

class ProfileViewModel (
    private val getProfileUseCase: ViewProfileUseCase,
    application: Application
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()
    private val dataStoreManager = UserDataStoreManager(application)


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

    suspend fun clickLogout() {
        dataStoreManager.clearCredentials()
    }

    sealed interface State{
        data object Loading : State
        data class Show(
            val user: UserEntity
        ) : State
        data class Error(
            val text: String
        ) : State

    }
    companion object{
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val profileRepoImpl = ProfileRepoImpl(
                    profileNetworkDataSource = ViewProfileNetworkDataSource(
                        context = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                    ),
                    editProfileNetworkDataSource = null
                )

                val profileUseCase = ViewProfileUseCase(profileRepoImpl)

                return ProfileViewModel(profileUseCase, extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application) as T
            }
        }
    }
}