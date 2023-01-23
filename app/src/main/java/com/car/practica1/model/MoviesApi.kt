package com.car.practica1.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Url

interface MoviesApi {

    @GET
    fun getMovies(
        @Url url: String?
    ): Call<ArrayList<Movie>>

    @GET("movie_detail/{id}")  //getGameDetailApiary("98746")
    fun getMovieDetail(
        @Path("id") id: String?
    ): Call<MovieDetail>
}