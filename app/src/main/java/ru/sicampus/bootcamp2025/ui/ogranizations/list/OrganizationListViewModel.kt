package ru.sicampus.bootcamp2025.ui.ogranizations.list

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.CreationExtras
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.launch
import ru.sicampus.bootcamp2025.data.organizations.list.OrganizationListNetworkDataSource
import ru.sicampus.bootcamp2025.data.organizations.list.OrganizationListRepoImpl
import ru.sicampus.bootcamp2025.domain.organizations.GetOrganizationListUseCase


class OrganizationListViewModel(
    private val useCase: GetOrganizationListUseCase,
    application: Application
) : AndroidViewModel(application) {
    val listState = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = false,
            maxSize = 30
        )
    ) {
        println("Creating PagingSource")
        OrganizationListPagingSource(useCase::invoke)
    }.flow.cachedIn(viewModelScope)

    init {
        viewModelScope.launch {
            listState.collect { pagingData ->
                if (pagingData.toString().isEmpty()) {
                    println("No data in paging data.")
                } else {
                    println("Data received: $pagingData")
                }
            }
        }
    }
    companion object {
        @Suppress("UNCHECKED_CAST")
        val Factory: ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>, extras: CreationExtras): T {
                val repoImpl = OrganizationListRepoImpl(
                    dataSource = OrganizationListNetworkDataSource(
                        context = extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application)
                )

                val useCase = GetOrganizationListUseCase(repoImpl)

                return OrganizationListViewModel(useCase, extras[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as Application) as T
            }
        }
    }
}