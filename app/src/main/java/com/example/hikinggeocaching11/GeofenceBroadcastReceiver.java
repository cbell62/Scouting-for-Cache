// Purpose: handle events sent by Geofence

package com.example.hikinggeocaching11;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver
{

    // Core - handles the notification that pops up on screen when geofence triggered
    // - The BroadcastReceiver listens - onReceive triggers when event triggers-
    // - creates a toast(thing that pops up in screen)
    @Override
    public void onReceive(Context context, Intent intent)
    { // when triggered make a toast
        // TODO: This method is called when the BroadcastReceiver is receiving

        Toast.makeText(context, "Geofence triggered!", Toast.LENGTH_SHORT).show();
    }


}
