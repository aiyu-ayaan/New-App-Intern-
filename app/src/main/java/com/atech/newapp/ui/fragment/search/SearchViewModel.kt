package com.atech.newapp.ui.fragment.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.atech.core.repositories.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val repository: NewsRepository,

    ) : ViewModel() {
    val query = MutableStateFlow("corona")

    val searchResult = query.flatMapLatest {
        repository.getSearchResult(it).cachedIn(viewModelScope).asFlow()
    }

}