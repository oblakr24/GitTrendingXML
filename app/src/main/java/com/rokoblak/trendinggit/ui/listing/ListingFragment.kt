package com.rokoblak.trendinggit.ui.listing

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rokoblak.trendinggit.databinding.FragmentListingBinding
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ListingFragment : Fragment() {

    private val viewModel: ListingViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentListingBinding.inflate(inflater, container, false)

        val adapter = ListingAdapter {
            viewModel.onAction(it)
        }

        with(binding) {
            val layoutManager = binding.recycler.layoutManager as LinearLayoutManager

            // Pagination support - request a new page load when coming close enough to the end
            recycler.clearOnScrollListeners()
            recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (newState == RecyclerView.SCROLL_STATE_IDLE && layoutManager.itemCount > 0) {
                        val lastVisiblePos = layoutManager.findLastVisibleItemPosition()
                        if (layoutManager.itemCount - lastVisiblePos < THRESHOLD_NEW_PAGE_LOAD) {
                            viewModel.onAction(ListingAction.NextPageTriggerReached)
                        }
                    }
                }
            })

            recycler.adapter = adapter
            swiperefresh.setOnRefreshListener {
                viewModel.onAction(ListingAction.RefreshTriggered)
            }
        }

        viewLifecycleOwner.lifecycle.coroutineScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.items.onEach { items ->
                    binding.swiperefresh.isRefreshing = false
                    adapter.submitList(items)
                }.launchIn(this)
            }
        }

        return binding.root
    }

    companion object {
        private const val THRESHOLD_NEW_PAGE_LOAD = 5 // If we're less than 5 items before the last, trigger a new page load
    }
}
