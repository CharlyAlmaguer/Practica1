package com.car.practica1.model

import com.google.gson.annotations.SerializedName

data class Movie(
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
    @SerializedName("lat")
    val lat: Double,
    @SerializedName("lon")
    val lon: Double
)
