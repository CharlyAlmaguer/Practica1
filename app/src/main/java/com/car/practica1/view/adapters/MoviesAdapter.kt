package com.car.practica1.view.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.car.practica1.databinding.MovieItemBinding
import com.car.practica1.model.Movie
import com.car.practica1.view.activities.MainActivity

class MoviesAdapter(private val context: Context, private val movies: ArrayList<Movie>): RecyclerView.Adapter<MoviesAdapter.ViewHolder>() {

    class ViewHolder(view: MovieItemBinding): RecyclerView.ViewHolder(view.root) {
        val ivMovie = view.ivMovie
        val tvTitle = view.tvTitle
        val tvClasificacion = view.tvClasificacion
        val tvDuracion = view.tvDuracion
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MovieItemBinding.inflate(LayoutInflater.from(context))
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.tvTitle.text = movies[position].titulo
        holder.tvClasificacion.text = "Clasificación: "+movies[position].clasificacion
        holder.tvDuracion.text = "Duración: "+movies[position].duracion

        Glide.with(context)
            .load(movies[position].poster)
            .into(holder.ivMovie)

        //Capturamos los clicks
        holder.itemView.setOnClickListener {
            if (context is MainActivity) context.selectedMovie(movies[position])
        }
    }

    override fun getItemCount(): Int = movies.size

}