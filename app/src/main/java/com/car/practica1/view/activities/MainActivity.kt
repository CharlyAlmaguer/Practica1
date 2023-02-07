package com.car.practica1.view.activities

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import com.car.practica1.R
import com.car.practica1.databinding.ActivityMainBinding
import com.car.practica1.model.Movie
import com.car.practica1.model.MoviesApi
import com.car.practica1.util.Constants
import com.car.practica1.view.adapters.MoviesAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {

            val call = Constants.getRetrofit().create(MoviesApi::class.java).getMovies("movies")

            call.enqueue(object : Callback<ArrayList<Movie>> {

                override fun onResponse(
                    call: Call<ArrayList<Movie>>,
                    response: Response<ArrayList<Movie>>
                ) {

                    //Para que se vea el circulo de carga
                    binding.pbConexion.visibility = View.GONE

                    binding.rvMenu.layoutManager = LinearLayoutManager(this@MainActivity)
                    binding.rvMenu.adapter = MoviesAdapter(this@MainActivity, response.body()!!)
                }

                override fun onFailure(call: Call<ArrayList<Movie>>, t: Throwable) {
                    AlertDialog.Builder(this@MainActivity)
                        .setTitle("Error")
                        .setMessage("No hay conexion: ${t.message}")
                        .setPositiveButton("Entendido", DialogInterface.OnClickListener { _, _ ->
                            finish()
                        })
                        .create()
                        .show()
                }
            })
        }
    }
    //funcion para mandar el id a DetailActivity despues de seleccionar un elemento del Recycler
    fun selectedMovie(movie: Movie){

        val parametros = Bundle().apply {
            putString("id", movie.id)
            putDouble("lat",movie.lat)
            putDouble("lon",movie.lon)
        }

        print(movie.lat)
        print(movie.lon)

        val intent = Intent(this,DetailActivity::class.java).apply {
            putExtras(parametros)
        }

        startActivity(intent)
    }
}