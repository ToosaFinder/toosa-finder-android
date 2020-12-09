package com.toosafinder.MainScreen.MapMainScreen

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle

import android.util.Log
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.get
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.toosafinder.MainScreen.MapMainScreen.MapMainScreenViewModel
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.toosafinder.R
import com.toosafinder.api.events.GetEventsRes
import org.koin.android.viewmodel.ext.android.getViewModel

class MapMainScreenActivity :  FragmentActivity(), OnMapReadyCallback {

    companion object{
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 15

    }
// TODO:МБ надо вынести это в security

    private var locationPermissionGranted = false
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private var lastKnownLocation: Location? = null
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private lateinit var mapMainScreenViewModel: MapMainScreenViewModel
    private lateinit var map: GoogleMap

    private val TAG = "anusai"

    private lateinit var currentLocation: Location
    private val permissionCode = 101

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_main_screen)

        mapMainScreenViewModel = getViewModel()


        mapMainScreenViewModel.mapResult.observe(this@MapMainScreenActivity){ events ->
            events.finalize(
                    onSuccess = :: showAllMarkers,
                    onError = {
                        Log.e("Error", " " + it)
                    }
            )
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this@MapMainScreenActivity)

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@MapMainScreenActivity)




    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        mapMainScreenViewModel.getEvents()
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            getLocationPermission()
//            Log.d(TAG, "after permission location listener setting")
//            showMyPosition()
//        }else {
//            Log.d(TAG, "location listener setting")
//            showMyPosition()
//        }
//
//        val locationRequest = LocationRequest.create();
//        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
//        locationRequest.setInterval(20 * 1000);
//
//        val locationCallBack = object : LocationCallback() {
//            override fun onLocationResult(locationResult : LocationResult){
//                locationResult ?: return
//                for (location in locationResult.locations){
//                    if(location!=null)
//                    map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(location.latitude, location.longitude)))
//                }
//            }
//        }

//        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null)
        // Add a marker in Sydney and move the camera
//        val sydney = LatLng(-34.0, 151.0)
//        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
//
//        mMap.addMarker(MarkerOptions()
//            .position(sydney).title("Marker in Sydney")
//            .snippet("В Сиднее много котов"))

    }

    private fun showAllMarkers(eventsRes : GetEventsRes) {
        for(event in eventsRes.events){
            map.addMarker(MarkerOptions().position(LatLng(event.latitude.toDouble(),event.longitude.toDouble()))
                .title(event.name))
        }
    }

    private fun showMyPosition()  =
            try {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    Log.d(TAG, "Listener set")
                    if (it != null) {
                        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(it.latitude, it.longitude)))
                    } else {
                        map.moveCamera(CameraUpdateFactory.newLatLng(LatLng(-34.0, 151.0)))
                    }
                }
            }
            catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }


    private fun getLocationPermission() {
        /*
         * Request location permission, so that we can get the location of the
         * device. The result of the permission request is handled by a callback,
         * onRequestPermissionsResult.
         */
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            locationPermissionGranted = true
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                           permissions: Array<String>,
                                           grantResults: IntArray) {
        locationPermissionGranted = false
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.d(TAG, "Permission is OK")
                    locationPermissionGranted = true
                }
            }
        }
    }

}