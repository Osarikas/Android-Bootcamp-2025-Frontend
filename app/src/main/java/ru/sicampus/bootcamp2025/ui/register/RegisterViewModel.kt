package ru.sicampus.bootcamp2025.ui.register

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import ru.sicampus.bootcamp2025.data.UserDTO
import ru.sicampus.bootcamp2025.data.UserDataStoreManager
import ru.sicampus.bootcamp2025.data.profile.ProfileRepoImpl
import ru.sicampus.bootcamp2025.data.register.RegisterNetworkDataSource
import ru.sicampus.bootcamp2025.data.register.RegisterRepoImpl
import ru.sicampus.bootcamp2025.data.register.UserRegisterDTO
import ru.sicampus.bootcamp2025.domain.UserEntity
import ru.sicampus.bootcamp2025.domain.profile.ProfileRepo
import ru.sicampus.bootcamp2025.domain.register.RegisterUseCase
import ru.sicampus.bootcamp2025.domain.register.UserRegisterEntity
import ru.sicampus.bootcamp2025.ui.vlist.FreeVolunteersListViewModel
import kotlin.reflect.KClass

@Suppress("UNCHECKED_CAST")
class RegisterViewModel(private val registerUseCase: RegisterUseCase, application: Application) : AndroidViewModel(application) {

    private val _state = MutableStateFlow<State>(getStateShow())
    val state = _state.asStateFlow()
    private val _userData = MutableStateFlow(UserRegisterEntity())
    val userData = _userData.asStateFlow()
    private var _password: String? = null
    private val _user = MutableLiveData<UserDTO?>()
    val user: LiveData<UserDTO?> get() = _user
    private val userDataStoreManager = UserDataStoreManager(application)



    fun clickNext(name: String, email: String, password: String){
        _userData.value = _userData.value.copy(
            name = name,
            email = email,
            password = password
        )
        println(_userData.value)
    }
    fun clickRegister(phone: String, tg: String, about: String){
        _userData.value = _userData.value.copy(
            phoneNumber = phone,
            telegramUsername = tg,
            about = about,
            name = _userData.value.name,
            email = _userData.value.email,
            password = _userData.value.password
        )
        println(_userData.value)
    }

    init {
        _userData.value = UserRegisterEntity()
    }

    private suspend fun updateState(){
        _state.emit(
            getStateShow()
        )
        //registerUseCase.invoke(userData.value)
    }
    private fun getStateShow() : State.Show{
        return State.Show(
            errorText = "Error"
        )
    }
    fun getUserRegisterDTO(): UserRegisterDTO? {
        val user = _userData.value
        return UserRegisterDTO(
            email = user.email.toString(),
            name = user.name.toString(),
            password = _password.toString(),
            phoneNumber = user.phoneNumber,
            telegramUsername = user.telegramUsername,
            about = user.about,
        )
    }

    sealed interface State {
        data object Loading : State
        data class Show(
            val errorText: String?
        ): State
    }
    fun registerUser() {
        viewModelScope.launch {
            registerUseCase.invoke(_userData.value).fold(
                onSuccess = { userDto ->
                    _user.value = userDto
                    userDataStoreManager.saveCredentials(_userData.value.email.toString(), _userData.value.password.toString())
                    _state.emit(State.Show("Success"))
                },
                onFailure = { error ->
                    Log.e("Register", "Ошибка регистрации: ${error.message}")
                    _state.emit(State.Show(error.toString()))
                }
            )
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val registerRepoImpl = RegisterRepoImpl(
                    registerNetworkDataSource = RegisterNetworkDataSource()
                )

                val registerUseCase = RegisterUseCase(registerRepoImpl)

                return RegisterViewModel(registerUseCase, extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application) as T
            }
        }
    }
}