package com.rokoblak.trendinggit.data.repo

import com.rokoblak.trendinggit.data.repo.model.LoadErrorType
import com.rokoblak.trendinggit.data.service.model.GitRepository
import kotlinx.coroutines.flow.Flow

interface GitRepositoriesLoadingRepo {
    val loadResults: Flow<LoadResult>

    suspend fun loadNext()

    suspend fun reload()

    sealed interface LoadResult {
        data class LoadError(val type: LoadErrorType) : LoadResult
        object LoadingFirstPage : LoadResult
        data class Loaded(
            val loadedItems: List<GitRepository>,
            val loadingMore: Boolean,
        ) : LoadResult
    }
}
