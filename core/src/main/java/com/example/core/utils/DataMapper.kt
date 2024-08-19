package com.example.core.utils

import com.example.core.data.source.local.entity.MovieEntity
import com.example.core.data.source.remote.response.ResultsItem
import com.example.core.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

object DataMapper {
    private const val PATH_IMG = "https://image.tmdb.org/t/p/w500"

    fun mapListResponseToDomain(movieResponse: List<ResultsItem>): Flow<List<Movie>> {
        val movieList = ArrayList<Movie>()
        movieResponse.map {
            val movie = Movie(
                it.id,
                PATH_IMG+it.posterPath,
                it.title,
                it.overview,
                PATH_IMG+it.backdropPath,
                false
            )
            movieList.add(movie)
        }
        return flowOf(movieList)
    }

    fun mapDomainToEntity(movie: Movie) =
        MovieEntity(
            movie.id,
            movie.image,
            movie.title,
            movie.overview,
            movie.movieImage,
            movie.isFavorite
        )

    fun mapListEntityToDomain(listMovieEntity: List<MovieEntity>): List<Movie> =
        listMovieEntity.map {
            Movie(
                it.id,
                it.image,
                it.title,
                it.overview,
                it.movieImage,
                it.isFavorite
            )
        }

}