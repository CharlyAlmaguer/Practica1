package com.car.practica1.view.activities

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.car.practica1.R
import com.car.practica1.databinding.ActivityLocationBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class LocationActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var binding: ActivityLocationBinding

    //Para google maps
    private lateinit var map: GoogleMap

    //Para los permisos
    private var coarseLocationPermissionGranted = false
    private var fineLocationPermissionGranted = false

    companion object{
        const val PERMISO_LOCALIZACION = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLocationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment: SupportMapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
    }

    fun createMarker(){

        val bundle = intent.extras

        val lat = bundle!!.getDouble("lat",0.0)
        val lon = bundle!!.getDouble("lon",0.0)

        val coordinates = LatLng(lat, lon)
        val marker = MarkerOptions()
            .position(coordinates)
            .title("Movies")
            //.snippet("Cursos y diplomados en TIC")
            .icon(BitmapDescriptorFactory.fromResource(R.drawable.pin_location))


        map.addMarker(marker)
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f), 5000, null)
    }
}