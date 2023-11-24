package com.iphonecamera.allinone.cameraediting.util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import androidx.core.app.ActivityCompat;


public class GPSTracker implements LocationListener {
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 1;
    private static final long MIN_TIME_BW_UPDATES = 1;
    boolean canGetLocation = false;
    public boolean isGPSEnabled = false;
    boolean isNetworkEnabled = false;
    double latitude;
    Location location;
    protected LocationManager locationManager;
    double longitude;
    public final Context mContext;

    @Override // android.location.LocationListener
    public void onLocationChanged(Location location) {
    }

    @Override // android.location.LocationListener
    public void onProviderDisabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onProviderEnabled(String str) {
    }

    @Override // android.location.LocationListener
    public void onStatusChanged(String str, int i, Bundle bundle) {
    }

    public GPSTracker(Context context) {
        this.mContext = context;
        getLocation();
    }

    public Location getLocation() {
        try {
            this.isGPSEnabled = this.locationManager.isProviderEnabled("gps");
            Log.v("isGPSEnabled", "=" + this.isGPSEnabled);
            this.isNetworkEnabled = this.locationManager.isProviderEnabled("network");
            Log.v("isNetworkEnabled", "=" + this.isNetworkEnabled);
            if (!this.isGPSEnabled && !this.isNetworkEnabled) {
                showSettingsAlert();
                return this.location;
            }
            this.canGetLocation = true;
            if (this.isNetworkEnabled) {
                this.location = null;
                Log.d("Network", "Network");
                if (this.locationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_FINE_LOCATION") != 0) {
                        ActivityCompat.checkSelfPermission(this.mContext, "android.permission.ACCESS_COARSE_LOCATION");
                    }
                    this.location = this.locationManager.getLastKnownLocation("network");
                    if (this.location != null) {
                        this.latitude = this.location.getLatitude();
                        this.longitude = this.location.getLongitude();
                    }
                }
            }
            if (this.isGPSEnabled) {
                this.location = null;
                if (this.location == null) {
                    this.locationManager.requestLocationUpdates("gps", 1L, 1.0f, this);
                    Log.d("GPS Enabled", "GPS Enabled");
                    if (this.locationManager != null) {
                        this.location = this.locationManager.getLastKnownLocation("gps");
                        if (this.location != null) {
                            this.latitude = this.location.getLatitude();
                            this.longitude = this.location.getLongitude();
                        }
                    }
                }
            }
            return this.location;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void stopUsingGPS() {
        LocationManager locationManager = this.locationManager;
        if (locationManager != null) {
            locationManager.removeUpdates(this);
        }
    }

    public double getLatitude() {
        Location location = this.location;
        if (location != null) {
            this.latitude = location.getLatitude();
        }
        return this.latitude;
    }

    public double getLongitude() {
        Location location = this.location;
        if (location != null) {
            this.longitude = location.getLongitude();
        }
        return this.longitude;
    }

    public boolean canGetLocation() {
        return this.canGetLocation;
    }

    public void showSettingsAlert() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this.mContext);
        builder.setTitle("GPS is settings");
        builder.setMessage("GPS is not enabled. Do you want to go to settings menu?");
        builder.setPositiveButton("Settings", new DialogInterface.OnClickListener() { // from class: com.cameraediter.iphone11pro.GPSTracker.1
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                GPSTracker.this.mContext.startActivity(new Intent("android.settings.LOCATION_SOURCE_SETTINGS"));
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() { // from class: com.cameraediter.iphone11pro.GPSTracker.2
            @Override // android.content.DialogInterface.OnClickListener
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
            }
        });
        builder.show();
    }
}
