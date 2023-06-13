package com.rokoblak.trendinggit.ui.listing

import androidx.annotation.ColorInt
import androidx.recyclerview.widget.DiffUtil


sealed class ListingItem(open val id: Long, val viewType: ViewType) {
    data class Error(val isNetworkError: Boolean) :
        ListingItem("ERROR: $isNetworkError".hashCode().toLong(), ViewType.ERROR)

    object InitialLoad : ListingItem("INITIAL_LOAD".hashCode().toLong(), ViewType.INITIAL)
    data class Repo(
        override val id: Long,
        val name: String,
        val desc: String?,
        val authorImgUrl: String?,
        val authorName: String,
        val lang: String?,
        val stars: String,
        val showsLang: Boolean,
        @ColorInt val langColor: Int?,
    ) : ListingItem(id, ViewType.REPO)

    object ShimmerCell : ListingItem("SHIMMER_CELL".hashCode().toLong(), ViewType.SHIMMER)

    enum class ViewType {
        ERROR, INITIAL, REPO, SHIMMER,
    }

    companion object {
        val DIFF_CALLBACK: DiffUtil.ItemCallback<ListingItem> =
            object : DiffUtil.ItemCallback<ListingItem>() {
                override fun areItemsTheSame(oldItem: ListingItem, newItem: ListingItem): Boolean =
                    oldItem.id == newItem.id

                override fun areContentsTheSame(
                    oldItem: ListingItem,
                    newItem: ListingItem
                ): Boolean = oldItem == newItem
            }
    }
}

