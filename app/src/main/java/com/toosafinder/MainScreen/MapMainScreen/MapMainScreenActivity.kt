package com.toosafinder.MainScreen.MapMainScreen

import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.toosafinder.R

class MapMainScreenActivity :  FragmentActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d("check", "check0")
        setContentView(R.layout.map_main_screen)
        if (getString(R.string.google_maps_key).isEmpty()) {
            Toast.makeText(this, "Add your own API key in MapWithMarker/app/secure.properties as MAPS_API_KEY=YOUR_API_KEY", Toast.LENGTH_LONG).show()
        }
        Log.d("check", "check1")
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment
        Log.d("check", "check2")
        mapFragment?.getMapAsync(this@MapMainScreenActivity)

        Log.d("check", "check3")

    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))

        mMap.addMarker(MarkerOptions()
            .position(sydney).title("Marker in Sydney")
            .snippet("В Сиднее много котов"))
    }
}