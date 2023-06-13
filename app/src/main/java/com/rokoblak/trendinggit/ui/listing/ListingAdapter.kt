package com.rokoblak.trendinggit.ui.listing

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.rokoblak.trendinggit.R
import com.rokoblak.trendinggit.databinding.ItemErrorBinding
import com.rokoblak.trendinggit.databinding.ItemInitialBinding
import com.rokoblak.trendinggit.databinding.ItemRepoBinding
import com.rokoblak.trendinggit.databinding.ItemShimmerBinding
import com.rokoblak.trendinggit.ui.listing.ListingItem.ViewType.*


class ListingAdapter(private val onAction: (ListingAction) -> Unit) :
    ListAdapter<ListingItem, ListingAdapter.ViewHolder<ListingItem>>(ListingItem.DIFF_CALLBACK) {

    @Suppress("UNCHECKED_CAST")
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        when (values()[viewType]) {
            REPO -> RepoHolder(ItemRepoBinding.inflate(parent.inflater(), parent, false))
            ERROR -> ErrorHolder(ItemErrorBinding.inflate(parent.inflater(), parent, false))
            INITIAL -> InitialHolder(ItemInitialBinding.inflate(parent.inflater(), parent, false))
            SHIMMER -> ShimmerHolder(ItemShimmerBinding.inflate(parent.inflater(), parent, false))
        } as ViewHolder<ListingItem>

    private fun ViewGroup.inflater() = LayoutInflater.from(context)

    override fun onBindViewHolder(holder: ViewHolder<ListingItem>, position: Int) {
        holder.bind(getItem(position))
    }

    override fun getItemViewType(position: Int): Int = getItem(position).viewType.ordinal

    abstract inner class ViewHolder<T : ListingItem>(view: View) : RecyclerView.ViewHolder(view) {
        open fun bind(item: T) = Unit
    }

    inner class ErrorHolder(private val binding: ItemErrorBinding) :
        ViewHolder<ListingItem.Error>(binding.root) {

        init {
            binding.buttonRetry.setOnClickListener {
                onAction(ListingAction.RefreshTriggered)
            }
        }

        override fun bind(item: ListingItem.Error) {
            with(binding) {
                tvTitle.setText(if (item.isNetworkError) R.string.error_no_connection else R.string.error_generic)
                tvDesc.setText(if (item.isNetworkError) R.string.error_no_connection_desc else R.string.error_generic_desc)
            }
        }
    }

    inner class ShimmerHolder(binding: ItemShimmerBinding) :
        ViewHolder<ListingItem.ShimmerCell>(binding.root) {
        override fun bind(item: ListingItem.ShimmerCell) = Unit
    }

    inner class InitialHolder(binding: ItemInitialBinding) :
        ViewHolder<ListingItem.InitialLoad>(binding.root) {
        override fun bind(item: ListingItem.InitialLoad) = Unit
    }

    inner class RepoHolder(private val binding: ItemRepoBinding) :
        ViewHolder<ListingItem.Repo>(binding.root) {

        override fun bind(item: ListingItem.Repo) {
            with(binding) {
                if (item.authorImgUrl != null) {
                    ivAuthor.isInvisible = false
                    ivAuthor.load(item.authorImgUrl) {
                        crossfade(true)
                        transformations(CircleCropTransformation())
                    }
                } else {
                    ivAuthor.isInvisible = true
                }
                tvAuthorName.text = item.authorName
                tvDesc.text = item.desc
                tvRepoName.text = item.name
                tvLang.text = item.lang
                tvStars.text = item.stars

                tvLang.isVisible = item.showsLang
                circle.isVisible = item.showsLang
                if (item.langColor != null) {
                    circle.setCircleColor(item.langColor)
                }
            }
        }
    }
}
