package com.example.core.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Movie(
    val id: Int?,
    val image: String?,
    val title: String?,
    val overview: String?,
    val movieImage: String?,
    val isFavorite: Boolean?,
): Parcelable