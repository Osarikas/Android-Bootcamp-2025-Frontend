package ru.sicampus.bootcamp2025.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import ru.sicampus.bootcamp2025.data.login.LoginNetworkDataSource
import ru.sicampus.bootcamp2025.data.login.LoginRepoImpl
import ru.sicampus.bootcamp2025.data.organizations.list.OrganizationListNetworkDataSource
import ru.sicampus.bootcamp2025.data.organizations.list.OrganizationListRepoImpl
import ru.sicampus.bootcamp2025.domain.login.LoginUseCase
import ru.sicampus.bootcamp2025.domain.organizations.GetOrganizationListUseCase
import ru.sicampus.bootcamp2025.ui.ogranizations.list.OrganizationListViewModel

class LoginViewModel(
    private val useCase: LoginUseCase,
    application: Application
) : AndroidViewModel(application) {

    private val dataStoreManager = UserDataStoreManager(application)

    init {
        viewModelScope.launch {
            val email = dataStoreManager.emailFlow.first()
            val password = dataStoreManager.passwordFlow.first()

            if (email != "" && password != "") {
                login(email, password)
            }
            else {
                _state.value = LoginState.Idle
            }
        }
    }
    fun login(email: String, password: String) {
        _state.value = LoginState.Loading

        viewModelScope.launch {
            val result = useCase.invoke(email, password)
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
    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val repoImpl = LoginRepoImpl(
                    dataSource = LoginNetworkDataSource()
                )

                val useCase = LoginUseCase(repoImpl)

                return LoginViewModel(
                    useCase, extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                ) as T
            }
        }
    }
}