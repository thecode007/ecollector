package com.thecode007.ecollecter.ui

import android.Manifest
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.rx2androidnetworking.Rx2AndroidNetworking
import com.thecode007.ecollecter.R
import com.thecode007.ecollecter.network.APIEndPoint
import com.thecode007.ecollecter.network.model.LogInResponse
import com.thecode007.ecollecter.network.model.User
import com.thecode007.ecollecter.network.model.UserResponse
import com.thecode007.ecollecter.rx.AppSchedulerProvider
import io.reactivex.observers.DisposableSingleObserver


class MapsFragment : Fragment(), DialogInterface.OnClickListener {
    private var fusedLocationClient: FusedLocationProviderClient? = null
    var map: GoogleMap? = null
    var remoteUsers: ArrayList<User>? = null
    var currentUser : User ? = null
    var myLocation : Marker? = null



    private val callback = OnMapReadyCallback { googleMap ->
        map = googleMap
        onMap()
        googleMap.setOnMarkerClickListener { marker ->
            if (marker.tag != null && remoteUsers != null) {
                currentUser = marker.tag as User
                val dialog = MaterialAlertDialogBuilder(requireContext())
                        .setTitle("Collect Data")
                        .setMessage("Check Bill of " + currentUser!!.name + " Harrouf")
                        .setPositiveButton("Yes", this)
                    .setNegativeButton(
                        "Route to " + currentUser!!.name + " Harrouf") { dialogInterface, i ->
                            val intent = Intent(
                                Intent.ACTION_VIEW,
                                Uri.parse("http://maps.google.com/maps?daddr=" + currentUser!!.lat + "," + currentUser!!.lang)
                            )
                            startActivity(intent)
                    }
                dialog.show()
            }
            true
        }
        getUsers()
    }

    fun onMap() {

        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        fusedLocationClient!!.lastLocation
            .addOnSuccessListener(requireActivity()) { location ->
                if (location != null) {
                val lat = LatLng(location.latitude, location.longitude)
                if (myLocation != null) {
                    myLocation!!.remove()
                }
                myLocation = map!!.addMarker(MarkerOptions().position(lat).icon(
                    BitmapDescriptorFactory.fromResource(R.drawable.marker_user)))
                    map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(lat, 5f))
                }
            }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_maps, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(callback)
    }

    private fun getUsers() {
        val sharedpreferences: SharedPreferences = requireActivity().getSharedPreferences(
            "ecollector",
            Context.MODE_PRIVATE
        )
        val logInResponse = Gson().fromJson(
            sharedpreferences.getString("profile", ""),
            LogInResponse::class.java
        )

        Rx2AndroidNetworking.get(APIEndPoint.GET_USERS)
            .addHeaders("Authorization", "Bearer " + logInResponse.accessToken)
            .build()
            .getParseSingle(object : TypeToken<UserResponse>() {
            })
            .subscribeOn(AppSchedulerProvider().io())
            .observeOn(AppSchedulerProvider().ui())
            .subscribe(object : DisposableSingleObserver<UserResponse>() {
                override fun onSuccess(response: UserResponse) {
                    if (response.users != null && response.users.size > 0 && map != null) {
                        remoteUsers = response.users
                        for (user in response.users) {
                            val position = LatLng(user.lat.toDouble(), user.lang.toDouble())
                            val marker = map!!.addMarker(
                                MarkerOptions().position(position).title(
                                    "Name: " + user.name + "\n"
                                            + "City: " + user.city
                                )
                            )
                            marker.tag = user
                            map!!.moveCamera(CameraUpdateFactory.newLatLngZoom(position, 5f))
                        }
                    }
                }

                override fun onError(e: Throwable) {
                    Toast.makeText(context, "asdasdsa", Toast.LENGTH_SHORT).show()
                }
            })
    }




    override fun onClick(p0: DialogInterface?, p1: Int) {
        if (currentUser != null) {
            val intent = Intent(requireActivity(), CollectorActivity::class.java)
            intent.putExtra("USER", Gson().toJson(currentUser))
            startActivity(intent)
        }
    }

}



