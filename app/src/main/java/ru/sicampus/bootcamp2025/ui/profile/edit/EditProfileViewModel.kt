package ru.sicampus.bootcamp2025.ui.profile.edit

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.data.dto.UserDTO
import ru.sicampus.bootcamp2025.data.profile.EditProfileNetworkDataSource
import ru.sicampus.bootcamp2025.data.profile.ProfileRepoImpl
import ru.sicampus.bootcamp2025.data.profile.ViewProfileNetworkDataSource
import ru.sicampus.bootcamp2025.domain.entities.UserEntity
import ru.sicampus.bootcamp2025.domain.profile.EditProfileUseCase
import ru.sicampus.bootcamp2025.domain.profile.ViewProfileUseCase
import ru.sicampus.bootcamp2025.ui.profile.view.ProfileViewModel
import java.util.Date

class EditProfileViewModel(
    private val useCase: EditProfileUseCase,
    application: Application
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()



    fun clickSave(
        fullName: String? = null,
        date: Date? = null,
        phone: String? = null,
        email: String? = null,
        telegram: String? = null,
        about: String? = null,
    ) {
        viewModelScope.launch{
            val user = UserEntity(
                email = email,
                name = fullName,
                birthDate = date,
                phoneNumber = phone,
                telegramUsername = telegram,
                about = about,
            )
            println(user)
            _state.emit(
                useCase.invoke(user).fold(
                    onSuccess = { data ->
                        State.Success(data)
                    },
                    onFailure = { e ->
                        State.Error(e.message)
                    }
                )
            )
        }

    }


    sealed interface State{
        data object Loading : State
        data class Success(
            val user: UserEntity
        ) : State
        data class Error(
            val text: String?
        ) : State

    }
    companion object{
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val profileRepoImpl = ProfileRepoImpl(
                    profileNetworkDataSource = null,
                    editProfileNetworkDataSource = EditProfileNetworkDataSource(
                        context = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application
                    )
                )

                val editProfileUseCase = EditProfileUseCase(profileRepoImpl)

                return EditProfileViewModel(editProfileUseCase, extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application) as T
            }
        }
    }
}