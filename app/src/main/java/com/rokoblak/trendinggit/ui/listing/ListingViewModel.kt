package com.rokoblak.trendinggit.ui.listing

import android.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rokoblak.trendinggit.data.repo.GitRepositoriesLoadingRepo
import com.rokoblak.trendinggit.data.repo.model.LoadErrorType
import com.rokoblak.trendinggit.data.service.model.GitRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ListingViewModel @Inject constructor(
    private val repo: GitRepositoriesLoadingRepo
) : ViewModel() {

    val items = repo.loadResults.map { loadResult ->
        when (loadResult) {
            is GitRepositoriesLoadingRepo.LoadResult.LoadError -> listOf(ListingItem.Error(isNetworkError = loadResult.type == LoadErrorType.NoNetwork))
            is GitRepositoriesLoadingRepo.LoadResult.Loaded -> {
                val items = loadResult.loadedItems.map {
                    it.toUI()
                }
                if (loadResult.loadingMore) {
                    items + listOf(ListingItem.ShimmerCell)
                } else {
                    items
                }
            }
            GitRepositoriesLoadingRepo.LoadResult.LoadingFirstPage -> listOf(ListingItem.InitialLoad)
        }
    }

    fun onAction(act: ListingAction) {
        viewModelScope.launch {
            when (act) {
                ListingAction.NextPageTriggerReached -> repo.loadNext()
                ListingAction.RefreshTriggered -> repo.reload()
            }
        }
    }

    private fun GitRepository.toUI() = ListingItem.Repo(
        id = id,
        name = name,
        desc = desc,
        authorImgUrl = authorImgUrl,
        authorName = authorName,
        lang = lang,
        stars = stars.toString(),
        showsLang = lang != null,
        langColor = lang?.mapLangToColor(),
    )

    private fun String.mapLangToColor() = colorMap[lowercase()]?.let {
        Color.parseColor(it)
    } ?: Color.GRAY

    // API Does not return this, so this is just a quick mapping
    private val colorMap = mapOf(
        "javascript" to "#F1E05A",
        "python" to "#3572A5",
        "java" to "#B07219",
        "ruby" to "#701516",
        "php" to "#4F5D95",
        "c++" to "#F34B7D",
        "c#" to "#178600",
        "typescript" to "#2B7489",
        "shell" to "#89e051",
        "c" to "#555555",
        "swift" to "#FFAC45",
        "go" to "#00ADD8",
        "kotlin" to "#F18E33",
        "rust" to "#DEA584",
        "scala" to "#C22D40"
    )
}
