package ru.sicampus.bootcamp2025.ui.ogranizations.list

import androidx.paging.PagingSource
import androidx.paging.PagingState
import ru.sicampus.bootcamp2025.domain.entities.OrganizationEntity

class OrganizationListPagingSource(
    private val request: suspend (pageNum : Int, pageSize : Int) -> Result<List<OrganizationEntity>>
) : PagingSource<Int, OrganizationEntity>() {
    override fun getRefreshKey(state: PagingState<Int, OrganizationEntity>): Int? {
        return state.anchorPosition?.let {
            state.closestPageToPosition(it)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(it)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, OrganizationEntity> {
        val pageNum = params.key ?: 0
        return request.invoke(
            pageNum,
            params.loadSize
        ).fold(
            onSuccess = { value ->
                LoadResult.Page(
                    data = value,
                    prevKey = (pageNum - 1).takeIf { it >= 0 },
                    nextKey = (pageNum + 1).takeIf { value.size == params.loadSize }
                )
            },
            onFailure = { error ->
                LoadResult.Error(error)
            }
        )
    }

}