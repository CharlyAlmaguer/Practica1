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

class LocationActivity : AppCompatActivity(), OnMapReadyCallback, LocationListener {

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

    private fun updateOrRequestPermissions() {

        //Revisando los permisos
        val hasCoarseLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val hasFineLocationPermission = ContextCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        coarseLocationPermissionGranted = hasCoarseLocationPermission
        fineLocationPermissionGranted = hasFineLocationPermission

        //Solicitando los permisos

        val permissionsToRequest = mutableListOf<String>()

        if (!hasCoarseLocationPermission)
            permissionsToRequest.add(Manifest.permission.ACCESS_COARSE_LOCATION)

        if (!hasFineLocationPermission)
            permissionsToRequest.add(Manifest.permission.ACCESS_FINE_LOCATION)


        if (permissionsToRequest.isNotEmpty()) {
            ActivityCompat.requestPermissions(
                this,
                permissionsToRequest.toTypedArray(),
                PERMISO_LOCALIZACION
            )
        } else {
            //Tenemos los permisos
            map.isMyLocationEnabled = true
            //Ubicacion del usuario
            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2000, 10F, this)

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            PERMISO_LOCALIZACION -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //Se obtuvo el permiso
                    updateOrRequestPermissions()
                } else {
                    if (shouldShowRequestPermissionRationale(permissions[0])) {
                        AlertDialog.Builder(this)
                            .setTitle("Permiso requerido")
                            .setMessage("Se necesita acceder a la ubicación para esta funcionalidad")
                            .setPositiveButton(
                                "Entendido",
                                DialogInterface.OnClickListener { _, _ ->
                                    updateOrRequestPermissions()
                                })
                            .setNegativeButton(
                                "Salir",
                                DialogInterface.OnClickListener { dialog, _ ->
                                    dialog.dismiss()
                                    finish()
                                })
                            .create()
                            .show()
                    } else {
                        //Si el usuario no quiere que nunca se le vuelva a preguntar por el permiso
                        Toast.makeText(this, "El permiso a la localización se ha negado permanentemente", Toast.LENGTH_LONG).show()
                        finish()
                    }
                }
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        createMarker()
        updateOrRequestPermissions()
    }

    override fun onRestart() {
        super.onRestart()
        if(!::map.isInitialized) return
        if (!fineLocationPermissionGranted){
            updateOrRequestPermissions()
        }
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
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(coordinates, 15f), 4000, null)
    }

    override fun onLocationChanged(location: Location) {

    }
}