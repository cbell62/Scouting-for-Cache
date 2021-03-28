// Purpose: make Geofences easier

package com.example.hikinggeocaching11;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingRequest;
import com.google.android.gms.maps.model.LatLng;

public class GeofencerHelper extends ContextWrapper
{
    // Class variables:
    private static final String TAG = "GeofenceHelper";
    PendingIntent pendingIntent;   // idk what this does

    public GeofencerHelper(Context base)
    {// constructor
        super(base);
    }

    //
    public GeofencingRequest getGeofencingRequest(Geofence geofence)
    {
        GeofencingRequest geofenceRequestBuilder = new GeofencingRequest.Builder()
                .addGeofence(geofence)
                .setInitialTrigger(GeofencingRequest.INITIAL_TRIGGER_ENTER) // if we are alrdy in the geofence when building then trigger it
                .build();

        return geofenceRequestBuilder;
    }

    public Geofence getGeofence(String ID, LatLng latLng, float radius, int transitionTypes)
    { // core - create a new geofence
        Geofence geofenceBuilt = new Geofence.Builder()
                .setRequestId(ID)
                .setCircularRegion( latLng.latitude, latLng.longitude, radius)
                .setExpirationDuration(Geofence.NEVER_EXPIRE)
                .setTransitionTypes(transitionTypes)
                .setLoiteringDelay(5000)
                .build();

        return geofenceBuilt;
    }


    public PendingIntent getPendingIntent()
    {

        if (pendingIntent == null)
        {
            Intent intent = new Intent(this, GeofenceBroadcastReceiver.class);
            pendingIntent = pendingIntent.getBroadcast(this, 2607, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        }
        return pendingIntent;


    }

}
