package com.michelle_condon.is4401_finalyearproject.GoogleMaps;

import android.Manifest;
import android.app.PendingIntent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.FragmentActivity;

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
import com.michelle_condon.is4401_finalyearproject.R;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    //Code below is based on the Youtube Video "Geofencing | The ultimate tutorial | Create and monitor geofences", yoursTruly, https://www.youtube.com/watch?v=nmAtMqljH9M

    //Declaring Variables
    private static final String TAG = "MapsActivity";
    private GoogleMap mMap;
    private GeofencingClient geoFencingClient;
    private GeofenceHelper geofenceHelper;
    private final int FINE_LOCATION_ACCESS_REQUEST_CODE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        //Creation of the map upon loading the page
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        assert mapFragment != null;
        mapFragment.getMapAsync(this);

        geoFencingClient = LocationServices.getGeofencingClient(this);
        geofenceHelper = new GeofenceHelper(this);
    }


    //This callback is triggered when the map is ready to be used.
    //If Google Play services is not installed on the device, the user will be prompted to install
    //it inside the SupportMapFragment. This method will only be triggered once the user has
    //installed Google Play services and returned to the app.
    //Version of map when it's ready for user interaction
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Add a marker in my home location and move the camera
        LatLng home = new LatLng(52.29364, -8.18674);
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(home, 4));
        enableUserLocation();
        mMap.clear();
        addMarker(home);
        float GEOFENCE_RADIUS = 100;
        addCircle(home, GEOFENCE_RADIUS);
        addGeofence(home, GEOFENCE_RADIUS);
    }

    //Method to enable access to user location
    private void enableUserLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                PackageManager.PERMISSION_GRANTED) {
            mMap.setMyLocationEnabled(true);
        } else {
            //Ask for permission
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission
                    .ACCESS_FINE_LOCATION}, FINE_LOCATION_ACCESS_REQUEST_CODE);
        }
    }

    //Method to manage the result of the permission request for location services
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == FINE_LOCATION_ACCESS_REQUEST_CODE) {
            //WE have permission
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    mMap.setMyLocationEnabled(true);
                } else {
                    Toast.makeText(this, "You must grant location access in order to use this service", Toast.LENGTH_LONG).show();
                }
            }
        }
        //Same as above code but used for API versions over 28 which require background access location
        int BACKGROUND_LOCATION_ACCESS_REQUEST_CODE = 10002;
        if (requestCode == BACKGROUND_LOCATION_ACCESS_REQUEST_CODE) {
            //WE have permission
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "You can add geofences...", Toast.LENGTH_SHORT).show();
                } else {
                    //We don't have permission
                    Toast.makeText(this, "You must grant location access in order to use this service",
                            Toast.LENGTH_SHORT).show();
                }
            }

        }
    }


    //Method to add the geofence
    private void addGeofence(LatLng latLng, float radius) {
        String GEOFENCE_ID = "SOME_GEOFENCE_ID";
        Geofence geofence = geofenceHelper.getGeofence(GEOFENCE_ID, latLng, radius, Geofence
                .GEOFENCE_TRANSITION_ENTER | Geofence.GEOFENCE_TRANSITION_DWELL | Geofence.GEOFENCE_TRANSITION_EXIT);
        GeofencingRequest geofencingRequest = geofenceHelper.getGeofencingReuest(geofence);
        PendingIntent pendingIntent = geofenceHelper.getPendingIntent();
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        //Adding geofences
        geoFencingClient.addGeofences(geofencingRequest, pendingIntent)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "onSuccess: Geofence Added..."))
                .addOnFailureListener(e -> {
                    String errorMessage = geofenceHelper.getErrorString(e);
                    Log.d(TAG, "onFailure: " + errorMessage);
                });
    }

    //Adding the map marker
    private void addMarker(LatLng latLng) {
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        mMap.addMarker(markerOptions);
    }

    //Adding the radius circle
    private void addCircle(LatLng latLng, float radius) {
        CircleOptions circleOptions = new CircleOptions();
        circleOptions.center(latLng);
        circleOptions.radius(radius);
        circleOptions.strokeColor(Color.argb(255, 255, 0, 0));
        circleOptions.fillColor(Color.argb(255, 255, 0, 0));
        circleOptions.strokeWidth(4);
        mMap.addCircle(circleOptions);
    }
}
//End