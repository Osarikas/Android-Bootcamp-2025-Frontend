package ru.sicampus.bootcamp2025.ui.volunteers.free

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.data.volunteers.FreeVolunteerNetworkDataSource
import ru.sicampus.bootcamp2025.data.volunteers.FreeVolunteerRepoImpl
import ru.sicampus.bootcamp2025.domain.volunteers.free.GetFreeVolunteersUseCase
import ru.sicampus.bootcamp2025.domain.entities.UserEntity


class FreeVolunteersListViewModel(
    private val getFreeVolunteersUseCase: GetFreeVolunteersUseCase,
    application: Application
) : AndroidViewModel(application) {
    private val _state = MutableStateFlow<State>(State.Loading)
    val state = _state.asStateFlow()

    init {
        updateState()
    }

    fun clickRefresh(){
        updateState()
    }

    private fun updateState() {
        viewModelScope.launch {
            _state.emit(State.Loading)
            _state.emit(
                getFreeVolunteersUseCase.invoke().fold(
                    onSuccess = {
                        data -> State.Show(data)
                    },
                    onFailure = {error ->
                        println(error.message)
                        State.Error(error.message.toString())

                    }
            )
            )
        }
    }

    sealed interface State{
        data object Loading: State
        data class Show(
            val items: List<UserEntity>
        ): State
        data class Error(
            val text: String
        ):State
    }
    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val freeVolunteerRepoImpl = FreeVolunteerRepoImpl(
                    freeVolunteerNetworkDataSource = FreeVolunteerNetworkDataSource(
                        context = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                )

                val freeVolunteersUseCase = GetFreeVolunteersUseCase(freeVolunteerRepoImpl)

                return FreeVolunteersListViewModel(freeVolunteersUseCase, extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application) as T
            }
        }
    }
}