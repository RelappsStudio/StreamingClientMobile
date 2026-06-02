package com.relapps.localstreaming.stories.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relapps.localstreaming.stories.domain.StoriesRepository
import com.relapps.localstreaming.stories.domain.StoryPage
import com.relapps.localstreaming.stories.domain.UserStoryGroup
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject



data class RibbonState(
    val storyGroups: List<UserStoryGroup> = emptyList(),
    val isLoading: Boolean = false
)

@HiltViewModel
 class StoriesRibbonViewModel @Inject constructor(
     private val repository: StoriesRepository
 ) : ViewModel() {
    private val _state = MutableStateFlow(RibbonState())
    val state = _state.asStateFlow()

    init {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true) }
            repository.getLatestStories().onSuccess { groups ->
                _state.update { it.copy(isLoading = false, storyGroups = groups) }
            }
        }
    }
 }
