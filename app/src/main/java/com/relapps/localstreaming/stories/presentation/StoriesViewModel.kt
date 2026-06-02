package com.relapps.localstreaming.stories.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.navigation.toRoute
import com.relapps.localstreaming.navigation.Screen
import com.relapps.localstreaming.stories.domain.StoriesRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface StoriesEffect {
    object ExitStories: StoriesEffect
}

@HiltViewModel
class StoriesViewModel @Inject constructor(
    private val repository: StoriesRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _state= MutableStateFlow(StoriesState())
    val state = _state.asStateFlow()

    private val _effect = Channel<StoriesEffect>()
    val effect = _effect.receiveAsFlow()

    init {
        val route = savedStateHandle.toRoute<Screen.Stories>()
        _state.update { it.copy(currentGroupIndex = route.initialGroupIndex, currentPageIndex = 0) }
        onAction(StoriesAction.LoadStories)
    }

    fun onAction(action: StoriesAction) {
        when(action) {
            is StoriesAction.GroupSwiped -> handleGroupSwipe(action.newGroupIndex)
            StoriesAction.LoadStories -> fetchStories()
            StoriesAction.NextPage -> handleNextPage()
            StoriesAction.PreviousPage -> handlePreviousPage()
        }
    }

    private fun fetchStories() {
        viewModelScope.launch {
            _state.update { it.copy(isLoading = true, error = null) }

            repository.getLatestStories()
                .onSuccess { groups ->
                    _state.update { it.copy(isLoading = false, storyGroup = groups) }
                }
                .onFailure { exception ->
                    _state.update { it.copy(isLoading = false, error = exception.localizedMessage) }
                }
        }
    }

    private fun handleNextPage() {
        val currentState = _state.value
        val currentGroup = currentState.storyGroup.getOrNull(currentState.currentGroupIndex)

        if (currentState.currentPageIndex < (currentGroup?.pages?.lastIndex ?: 0)) {
            _state.update { it.copy(currentPageIndex = currentState.currentPageIndex + 1) }
        } else if (currentState.currentGroupIndex < currentState.storyGroup.lastIndex) {
            _state.update {
                it.copy(currentGroupIndex = currentState.currentGroupIndex + 1, currentPageIndex = 0)
            }
        } else {
            viewModelScope.launch {
                _effect.send(StoriesEffect.ExitStories)
            }
        }
    }

    private fun handlePreviousPage() {
        val currentState = _state.value

        if (currentState.currentPageIndex > 0) {
            _state.update { it.copy(currentPageIndex = currentState.currentPageIndex - 1) }
        } else if (currentState.currentGroupIndex > 0) {
            val previousGroupIndex = currentState.currentGroupIndex - 1
            val prevGroup = currentState.storyGroup[previousGroupIndex]
            _state.update {
                it.copy(currentGroupIndex = previousGroupIndex, currentPageIndex = prevGroup.pages.lastIndex)
            }
        }
    }

    private fun handleGroupSwipe(newGroupIndex: Int) {
        _state.update {
            it.copy(currentGroupIndex = newGroupIndex, currentPageIndex = 0)
        }
    }
}