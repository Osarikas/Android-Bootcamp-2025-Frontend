package ru.sicampus.bootcamp2025.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import ru.sicampus.bootcamp2025.data.login.LoginNetworkDataSource

class LoginViewModel(
    private val api: LoginNetworkDataSource,
    private val dataStoreManager: UserDataStoreManager
) : ViewModel() {

    init {
        viewModelScope.launch {
            val email = dataStoreManager.emailFlow.first()
            val password = dataStoreManager.passwordFlow.first()

            if (email != "" && password != "") {
                login(email, password)
                _state.value = LoginState.Success
                println("success + ${email} + ${password}")
            }
            else {
                _state.value = LoginState.Idle
            }
        }
    }
    fun login(email: String, password: String) {
        _state.value = LoginState.Loading

        viewModelScope.launch {
            val result = api.login(email, password)
            result.onSuccess { data ->
                dataStoreManager.saveCredentials(email, password)
                _state.value = LoginState.Success
            }.onFailure { e ->
                _state.value = LoginState.Error(e.message ?: "Ошибка авторизации")
            }
        }
    }

    private val _state = MutableStateFlow<LoginState>(LoginState.Idle)
    val state: StateFlow<LoginState> = _state.asStateFlow()

    sealed class LoginState {
        object Idle : LoginState()
        object Loading : LoginState()
        object Success : LoginState()
        data class Error(val message: String) : LoginState()
    }
    class Factory(
        private val api: LoginNetworkDataSource,
        private val dataStoreManager: UserDataStoreManager
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return LoginViewModel(api, dataStoreManager) as T
        }
    }
}