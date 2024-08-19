package com.example.kaleapps.views.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import com.example.core.domain.usecase.MovieUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(movieUseCase: MovieUseCase) : ViewModel() {
    val getMovieNow = movieUseCase.getNowMoviePlaying().asLiveData()
    val getMoviePopular = movieUseCase.getMoviePopular().asLiveData()
    val getMovieTopRated = movieUseCase.getMovieTopRated().asLiveData()
}