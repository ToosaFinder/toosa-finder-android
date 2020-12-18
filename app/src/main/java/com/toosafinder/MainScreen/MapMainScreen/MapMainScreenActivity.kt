package com.toosafinder.MainScreen.MapMainScreen

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Location
import android.os.Bundle
import android.text.SpannableString
import android.text.style.ForegroundColorSpan

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.widget.TextView
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
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.toosafinder.R
import com.toosafinder.api.events.GetEventsRes
import org.koin.android.viewmodel.ext.android.getViewModel

class MapMainScreenActivity :  FragmentActivity(), OnMapReadyCallback {

    companion object{
        private const val PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION = 1
        private const val DEFAULT_ZOOM = 15

    }

    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val defaultLocation = LatLng(-33.8523341, 151.2106085)

    private lateinit var mapMainScreenViewModel: MapMainScreenViewModel
    private lateinit var map: GoogleMap
    private lateinit var locationRequest : LocationRequest
    private lateinit var  locationCallBack : LocationCallback

    private val TAG = "anusai"

    private lateinit var currentLocation: Location
    private val permissionCode = 101

    internal inner class EventInfoWidgetAdapter : GoogleMap.InfoWindowAdapter {
        private val window: View = layoutInflater.inflate(R.layout.event_info_window, null)
        private val contents: View = layoutInflater.inflate(R.layout.event_info_contents, null)

        override fun getInfoWindow(marker: Marker?): View {
            if (marker != null) {
                render(marker, window)
            }
            return window
            TODO("check calling getInfoContents")
        }

        override fun getInfoContents(marker: Marker?): View {
            if (marker != null) {
                render(marker, window)
            }
            return window
            TODO("check calling getInfoWindow")
        }

        private fun render(marker: Marker, view: View) {
            val description: String? = marker.title

            // Set the title and snippet for the custom info window
            val title: String? = marker.title
            val titleUi = view.findViewById<TextView>(R.id.title)

            if (title != null) {
                // Spannable string allows us to edit the formatting of the text.
                titleUi.text = SpannableString(title).apply {
                    setSpan(ForegroundColorSpan(Color.RED), 0, length, 0)
                }
            } else {
                titleUi.text = ""
            }

            val snippet: String? = marker.snippet
            val snippetUi = view.findViewById<TextView>(R.id.snippet)
            if (snippet != null && snippet.length > 12) {
                snippetUi.text = SpannableString(snippet).apply {
                    setSpan(ForegroundColorSpan(Color.MAGENTA), 0, 10, 0)
                    setSpan(ForegroundColorSpan(Color.BLUE), 12, snippet.length, 0)
                }
            } else {
                snippetUi.text = ""
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.map_main_screen)

        mapMainScreenViewModel = getViewModel()


        mapMainScreenViewModel.mapResult.observe(this@MapMainScreenActivity){ events ->
            events.finalize(
                    onSuccess = :: showAllMarkers,
                    onError = {
                        Log.e("Error", it.toString())
                    }
            )
        }

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        mapFragment?.getMapAsync(this@MapMainScreenActivity)

        fusedLocationProviderClient =  LocationServices.getFusedLocationProviderClient(this@MapMainScreenActivity)




    }


    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

        map.setInfoWindowAdapter(EventInfoWidgetAdapter())

        mapMainScreenViewModel.getEvents()

        locationRequest = LocationRequest.create();
        locationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        locationRequest.setInterval(20 * 1000);

        locationCallBack = object : LocationCallback() {
            override fun onLocationResult(locationResult : LocationResult?){
                locationResult ?: return
                for (location in locationResult.locations){
                    if(location!=null)
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom
                        (LatLng(location.latitude, location.longitude), DEFAULT_ZOOM.toFloat()))
                }
            }
        }

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            getLocationPermission()
        }else {
            showMyPosition()
        }
    }

    private fun showAllMarkers(eventsRes : GetEventsRes) {
        for(event in eventsRes.events){
            Log.d(TAG,"Latitude" + event.latitude + "Longitude" + event.longitude)
            map.addMarker(MarkerOptions().position(LatLng(event.latitude.toDouble(),event.longitude.toDouble()))
                .title(event.name))
        }
    }

    //по идее catch никогда ничего не поймает, во всех местах, где используется функция есть пермишн на местоположение
    private fun showMyPosition()  =
            try {
                fusedLocationProviderClient.lastLocation.addOnSuccessListener {
                    Log.d(TAG, "Listener set")
                    if (it != null) {
                        map.moveCamera(CameraUpdateFactory.newLatLngZoom
                        ((LatLng(it.latitude, it.longitude)), DEFAULT_ZOOM.toFloat()))
                    } else {
                        fusedLocationProviderClient.requestLocationUpdates(locationRequest, locationCallBack, null)
                    }
                }
            }
            catch (e: SecurityException) {
                Log.e("Exception: %s", e.message, e)
            }

    /**
     * Request location permission, so that we can get the location of the
     * device. The result of the permission request is handled by a callback,
     * onRequestPermissionsResult.
     */
    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION)
            == PackageManager.PERMISSION_GRANTED) {
            showMyPosition()
        } else {
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION)
            showMyPosition()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                           permissions: Array<String>,
                                           grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION -> {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.isNotEmpty() &&
                    grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    showMyPosition()
                }
            }
        }
    }

}