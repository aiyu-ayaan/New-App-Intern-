package com.atech.newapp.ui.fragment.home

import android.os.Bundle
import android.view.View
import android.viewbinding.library.fragment.viewBinding
import android.widget.Toast
import androidx.core.view.doOnPreDraw
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.recyclerview.widget.LinearLayoutManager
import com.atech.core.models.Article
import com.atech.newapp.R
import com.atech.newapp.databinding.FragmentHomeBinding
import com.atech.newapp.ui.utils.openCustomChromeTab
import com.atech.newapp.ui.utils.openShareDeepLink
import com.google.android.material.transition.MaterialSharedAxis
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : Fragment(R.layout.fragment_home) {

    private val binding: FragmentHomeBinding by viewBinding()
    private val viewModel: HomeViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enterTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ true)
        returnTransition = MaterialSharedAxis(MaterialSharedAxis.Y, /* forward= */ false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        postponeEnterTransition()
        view.doOnPreDraw { startPostponedEnterTransition() }
        val homeAdapter = HomeAdapter({ article ->
            navigateToDescription(article)
        }, { title, url ->
            activity?.openShareDeepLink(title, url)
        })
        binding.apply {
            showHeadings.apply {
                adapter = homeAdapter.withLoadStateHeaderAndFooter(
                    header = NewLoadStateAdapter { homeAdapter.retry() },
                    footer = NewLoadStateAdapter { homeAdapter.retry() }
                )
                layoutManager = LinearLayoutManager(requireContext())
            }
        }
        homeAdapter.loadStateFlow.asLiveData().observe(viewLifecycleOwner) { loadState ->
            binding.progressBarNews.isVisible = loadState.refresh is LoadState.Loading
            if (loadState.refresh is LoadState.Error) {
                Toast.makeText(
                    requireContext(),
                    "${(loadState.refresh as LoadState.Error).error}",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
        }
        lifecycleScope.launchWhenCreated {
            viewModel.topHeading.observe(viewLifecycleOwner) {
                homeAdapter.submitData(viewLifecycleOwner.lifecycle, it)
            }
        }
    }


    private fun navigateToDescription(article: Article) {
        context?.openCustomChromeTab(article.url!!)
    }
}