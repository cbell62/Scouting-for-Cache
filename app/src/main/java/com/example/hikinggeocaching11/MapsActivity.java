package com.example.hikinggeocaching11;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingClient;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.CircleOptions;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import android.content.Intent;
import android.view.View;
import android.widget.RadioGroup;
import android.widget.RadioButton;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener
{
    private GoogleMap mMap;
    private PendingIntent geofencePendingIntent;
    private GeofencingClient geofencingClient;
    private GeofencerHelper geofencerHelper;

    private int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;
    private final int GEOFENCE_RADIUS_DEFAULT = 100;
    private List<Geofence> geofenceList = new ArrayList<>();
    private ArrayList<LatLng> courses = new ArrayList<LatLng>();

    // Retrieve bundle data from previous activity - only way to get it

    //private String courseNumber = getIntent().getExtras().getString("key");
    //private String courseNumber = getIntent();
    //private int courseNumber2 = Integer.parseInt(courseNumber);


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        // Initialize/create geofencing client
        geofencingClient = LocationServices.getGeofencingClient(this);
        geofencerHelper = new GeofencerHelper(this);

    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        mMap = googleMap;
        // create LatLong then move camera to that location by default when map is ready
        LatLng princetonNJ = new LatLng(60.55279, -151.275812);
        // Create a marker at custom location - then title it
        mMap.addMarker(new MarkerOptions().position(princetonNJ).title("Custom Marker1"));
        mMap.moveCamera(CameraUpdateFactory.newLatLng(princetonNJ));

        enableUserLocation();
        mMap.setOnMapLongClickListener(this);

        //populate courses w/ hardcoded values
        int courseNumber = getIntent().getExtras().getInt("crNum");
        Log.d("keyVal:",""+courseNumber);
        LatLng course1 = new LatLng(60.568192, -151.251642);
        LatLng course2 = new LatLng(60.559441, -151.270195);
        LatLng course3 = new LatLng(60.560394, -151.270195);
        LatLng course4 = new LatLng(60.557441, -151.271195);
        LatLng course5 = new LatLng(60.559941, -151.270495);
        courses.add(course1);
        courses.add(course2);
        courses.add(course3);
        courses.add(course4);
        courses.add(course5);

        addGeofence(courses.get(courseNumber),GEOFENCE_RADIUS_DEFAULT);
        mMap.clear();
        addMarker(courses.get(courseNumber));
        addCircle(courses.get(courseNumber), GEOFENCE_RADIUS_DEFAULT);
        addGeofence(courses.get(courseNumber),GEOFENCE_RADIUS_DEFAULT);
    }

    private void enableUserLocation()
    {
        // Check to see if FINE_LOCATION is enabled - its required to work for geofencing
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            // if permission is granted then enable the user location
            // mMap is the google map
            mMap.setMyLocationEnabled(true);
        }
        // ask for the permission
        else
            {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION))
            {
                // FINE_LOCATION_ACCESS_REQUEST_CODE = 10,001
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                        , FINE_LOCATION_ACCESS_REQUEST_CODE);
            } else
                {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}
                        , FINE_LOCATION_ACCESS_REQUEST_CODE);
            }

        }
    }


    // THis makes the go to my locaiton button
    @SuppressLint("MissingPermission")
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults)
    {
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE)
        {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                mMap.setMyLocationEnabled(true);
            }
        }
    }


    // TODO:1
    @Override
    public void onMapLongClick(LatLng latLng)
    {
        // Handling backgroundPermission
        if(Build.VERSION.SDK_INT >= 29)
        {
            if(ContextCompat.checkSelfPermission(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {
                // Clear , addMarker(optional), add circle to show geofence(needed for user), addGeofence
                mMap.clear();
                addMarker(latLng);
                addCircle(latLng, GEOFENCE_RADIUS_DEFAULT);
                addGeofence(latLng, GEOFENCE_RADIUS_DEFAULT);
            }
            else
            {
                if(ActivityCompat.shouldShowRequestPermissionRationale(this,Manifest.permission.ACCESS_BACKGROUND_LOCATION))
                {
                    // Potential Error: made up request code
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                    , 29001);
                }
                else
                {
                    // Potential Error: made up request code
                    ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_BACKGROUND_LOCATION}
                            , 29001);
                }
            }
        }
        else
        {
            mMap.clear();
            addMarker(latLng);
            addCircle(latLng, GEOFENCE_RADIUS_DEFAULT);
            addGeofence(latLng, GEOFENCE_RADIUS_DEFAULT);
        }
    }

    /*
    private void tryAddingGeofence(LatLng latLng)
    {
        mMap.clear();
        addMarker(latLng);
        addCircle(latLng, GEOFENCE_RADIUS_DEFAULT);
        addGeofence(latLng, GEOFENCE_RADIUS_DEFAULT);
    }
    */

    // Core: implements GeofenceHelper file
    @SuppressLint("MissingPermission")
    private void addGeofence(LatLng latLng, float radius)
    {
        // A new id is needed for each geofence or it will overwrite
        Geofence geofence = geofencerHelper.getGeofence("customID1", latLng, radius
                , Geofence.GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL
                        | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofencerHelper.getGeofencingRequest(geofence);
        PendingIntent pendingIntent = geofencerHelper.getPendingIntent();
        geofencingClient.addGeofences(geofencingRequest, pendingIntent);
    }

    private  void addMarker(LatLng latLng)
    {
        // create markerOptions (lat & long)
        // create marker from markerOptions
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    private void addCircle(LatLng latLng, float radius)
    {
        // create circleOptions object - fill out the options - add the circle
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.rgb(253,85,235));
        circleOptions.fillColor(Color.rgb(34,86,235));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }




}

