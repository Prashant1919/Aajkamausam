package com.example.weatherapp

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContract
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import com.example.weatherapp.databinding.ActivityMainBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

private lateinit var binding: ActivityMainBinding
private lateinit var fusedlocationclient: FusedLocationProviderClient

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fusedlocationclient=LocationServices.getFusedLocationProviderClient(this)
        binding.btn.setOnClickListener{
            fetchlocation()
        }

    }

    private fun fetchlocation() {
        val task= fusedlocationclient.lastLocation
       if(ActivityCompat.checkSelfPermission(this,android.Manifest.permission.ACCESS_FINE_LOCATION)!=PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this,android.Manifest
               .permission.ACCESS_COARSE_LOCATION)!=PackageManager.PERMISSION_GRANTED){
           ActivityCompat.requestPermissions(this,arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION,android.Manifest.permission.ACCESS_COARSE_LOCATION),101)
           return
       }
        task.addOnSuccessListener {
            if(it!=null){
                Toast.makeText(this,"${it.longitude}",Toast.LENGTH_LONG).show()
                Toast.makeText(this,"${it.latitude}",Toast.LENGTH_LONG).show()

            }
        }

    }
}