package com.car.practica1.model

import com.google.gson.annotations.SerializedName

data class MovieDetail(
    @SerializedName("id")
    val id: String,
    @SerializedName("titulo")
    val titulo: String,
    @SerializedName("poster")
    val poster: String,
    @SerializedName("clasificacion")
    val clasificacion: String,
    @SerializedName("duracion")
    val duracion: String,
    @SerializedName("sinopsis")
    val sinopsis: String,
    @SerializedName("calificacion")
    val calificacion: String,
    @SerializedName("director")
    val director: String
)
