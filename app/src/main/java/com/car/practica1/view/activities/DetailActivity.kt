package com.car.practica1.view.activities

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.bumptech.glide.Glide
import com.car.practica1.databinding.ActivityDetailBinding
import com.car.practica1.model.MovieDetail
import com.car.practica1.model.MoviesApi
import com.car.practica1.util.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.extras

        val id = bundle?.getString("id","0")

        CoroutineScope(Dispatchers.IO).launch {

            val call = Constants.getRetrofit().create(MoviesApi::class.java).getMovieDetail(id)

            call.enqueue(object : Callback<MovieDetail> {

                override fun onResponse(call: Call<MovieDetail>, response: Response<MovieDetail>) {

                    binding.pbConexion.visibility = View.INVISIBLE

                    binding.apply {

                        collapsingtoolbar.title = response.body()?.titulo
                        tvDetailCalificacion.text = "Calificación: "+response.body()?.calificacion
                        tvDetailClasificacion.text = "Clasificación: "+response.body()?.clasificacion
                        tvDetailDuracion.text = "Duración: "+response.body()?.duracion
                        tvDetailSinopsis.text = "Sinopsis: \n"+response.body()?.sinopsis
                        tvDetailDirector.text = "Director/s: "+response.body()?.director

                        Glide.with(this@DetailActivity)
                            .load(response.body()?.poster)
                            .into(ivDetailPoster)

                        pbConexion.visibility = View.INVISIBLE
                    }

                }

                override fun onFailure(call: Call<MovieDetail>, t: Throwable) {
                    AlertDialog.Builder(this@DetailActivity)
                        .setTitle("Error")
                        .setMessage("No hay conexion: ${t.message}")
                        .setPositiveButton("Entendido", DialogInterface.OnClickListener { _, _ ->
                            finish()
                        })
                        .create()
                        .show()

                    binding.pbConexion.visibility = View.GONE
                }

            })

        }

    }
}