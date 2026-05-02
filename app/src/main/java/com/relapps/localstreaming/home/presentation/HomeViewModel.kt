package com.relapps.localstreaming.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.relapps.localstreaming.auth.domain.AuthStatus
import com.relapps.localstreaming.auth.domain.repository.AuthRepository
import com.relapps.localstreaming.home.domain.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.WhileSubscribed
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val movieRepository: MovieRepository
) : ViewModel() {

    @OptIn(ExperimentalCoroutinesApi::class)
    val state: StateFlow<HomeState> = authRepository.authState.flatMapLatest {
        state ->
        flow {
            when(state) {
                is AuthStatus.Authenticated -> emit(loadProdMovies())
                AuthStatus.Guest -> emit(loadFakeMovies())
                AuthStatus.Loading -> emit(HomeState(isLoading = true))
            }
        }
    }.stateIn(
        scope= viewModelScope,
        started= SharingStarted.WhileSubscribed(5000),
        initialValue = HomeState()
    )

    private suspend fun loadFakeMovies() : HomeState {
        val movies = movieRepository.getMovies()
        return HomeState(movies = movies, isGuest = true, isLoading = false)
    }

    private suspend fun loadProdMovies(): HomeState {
        val movies = movieRepository.getMovies()
        return HomeState(movies = movies, isGuest = true, isLoading = false)
    }


}