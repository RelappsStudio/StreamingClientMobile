package com.relapps.localstreaming.stories.presentation

sealed interface StoriesAction {
    object LoadStories: StoriesAction
    object NextPage: StoriesAction
    object PreviousPage: StoriesAction
    data class GroupSwiped(val newGroupIndex: Int) : StoriesAction
}