package geolocalizacao.poc.com.br.poc_geolocalizacao;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

/**
 * Created by rksc on 28/12/16.
 */
public class Geo {

    private static String TAG_LOG = "GEO";

    private static Intent broadcastIntent;

    public static void findLocation(final Context context) {

        Log.i(TAG_LOG, "findLocation init");

        // Acquire a reference to the system Location Manager
        LocationManager locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

        // Define a listener that responds to location updates
        LocationListener locationListener = new LocationListener() {

            public void onLocationChanged(Location location) {

                Log.i(TAG_LOG, "onLocationChanged");

                // Called when a new location is found by the network location provider.
                setLocation(location);

                //send notification
                sendLocationChanged(context);
            }

            public void onStatusChanged(String provider, int status, Bundle extras) {
                Log.i(TAG_LOG, "onStatusChanged");

            }

            public void onProviderEnabled(String provider) {
                Log.i(TAG_LOG, "onProviderEnabled");

            }

            public void onProviderDisabled(String provider) {
                Log.i(TAG_LOG, "onProviderDisabled");

            }
        };

        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i(TAG_LOG, "findLocation no permission");

            return;
        }

        NetworkInfo networkInfo = getNetworkInfo(context);

        if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE
                || networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {

            Log.i(TAG_LOG, "Provider NETWORK_PROVIDER");

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);

        } else {

            Log.i(TAG_LOG, "Provider GPS_PROVIDER");

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
        }

        Log.i(TAG_LOG, "findLocation end");
    }


    private static void sendLocationChanged(Context context) {

        Log.i(TAG_LOG, "sendLocationChanged");

        if (broadcastIntent == null) {
            broadcastIntent = new Intent();
        }

        broadcastIntent.setAction(MainActivity.GeoActivityReceiver.ACTION_RESP);
        broadcastIntent.addCategory(Intent.CATEGORY_DEFAULT);

        //notify
        context.sendBroadcast(broadcastIntent);
    }

    private static void setLocation(Location location) {
        MyLocation.getInstance().setLocation(location);
    }

    /***
     * devolve informacoes do rede.
     * @param context
     * @return
     */
    private static NetworkInfo getNetworkInfo(Context context) {

        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return connMgr.getActiveNetworkInfo();
    }
}
