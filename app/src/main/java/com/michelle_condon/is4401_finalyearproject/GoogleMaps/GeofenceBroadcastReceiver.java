package com.michelle_condon.is4401_finalyearproject.GoogleMaps;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.google.android.gms.location.Geofence;
import com.google.android.gms.location.GeofencingEvent;
import com.michelle_condon.is4401_finalyearproject.ClockIn_Screen;

import java.util.List;

public class GeofenceBroadcastReceiver extends BroadcastReceiver {

    //Code below is based on the Youtube Video "Geofencing | The ultimate tutorial | Create and monitor geofences", yoursTruly, https://www.youtube.com/watch?v=nmAtMqljH9M

    //Declare Variables
    private static final String TAG = "GeofenceBraodcastReceiv";

    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        NotificationHelper notificationHelper = new NotificationHelper(context);

        GeofencingEvent geofencingEvent = GeofencingEvent.fromIntent(intent);

        if (geofencingEvent.hasError()) {
            Log.d(TAG, "onReceive: Errr receiving geofence event...");
            return;
        }

        List<Geofence> geofenceList = geofencingEvent.getTriggeringGeofences();
        for (Geofence geofence : geofenceList) {
            Log.d(TAG, "onReceive: " + geofence.getRequestId());
        }
        // Location location = geofencingEvent.getTriggeringLocation();
        int transitionType = geofencingEvent.getGeofenceTransition();

        //If statements for when a user enters, dwells or exits the geofence
        if (transitionType == 1) {
            Toast.makeText(context, "Welcome to Solution Trail", Toast.LENGTH_SHORT).show();
            notificationHelper.sendHighPriorityNotification("You Have Entered Your Work Premises", "Please Clock In/Out", MapsActivity.class);
            Intent i = new Intent(context, ClockIn_Screen.class);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        } else if (transitionType == 4) {
            Toast.makeText(context, "GEOFENCE_TRANSITION_DWELL", Toast.LENGTH_SHORT).show();
            notificationHelper.sendHighPriorityNotification("GEOFENCE_TRANSITION_DWELL", "", MapsActivity.class);
        } else if (transitionType == 2) {
            Toast.makeText(context, "Have you clocked out from your shift?", Toast.LENGTH_SHORT).show();
            notificationHelper.sendHighPriorityNotification("Exited Work Premises", "Have you clocked out?", MapsActivity.class);
        }
    }
}
//End