package geolocalizacao.poc.com.br.poc_geolocalizacao;

import android.Manifest;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private static String TAG_LOG = "MainActivity";

    private GeoActivityReceiver receiver;

    TextView txtView = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //register receiver broadcast
        IntentFilter filter = new IntentFilter(GeoActivityReceiver.ACTION_RESP);
        filter.addCategory(Intent.CATEGORY_DEFAULT);
        receiver = new GeoActivityReceiver();
        registerReceiver(receiver, filter);


        // Register the listener with the Location Manager to receive location updates
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {

            Log.i(TAG_LOG, "findLocation no permission");

            ActivityCompat.requestPermissions((Activity) this, new String[] {
                            Manifest.permission.ACCESS_FINE_LOCATION,
                            Manifest.permission.ACCESS_COARSE_LOCATION },
                    1);

            // return;
        }


        txtView = (TextView)findViewById(R.id.txtLocation);

        Geo.findLocation(MainActivity.this);
    }

    @Override
    protected void onResume() {
        super.onResume();


    }

    /**
     * Receiver
     */
    public class GeoActivityReceiver extends BroadcastReceiver {

        public String GeoActivityReceiver;

        public static final String ACTION_RESP = "com.geo.intent.action.MESSAGE_PROCESSED";

        @Override
        public void onReceive(Context context, Intent intent) {

            Log.i(TAG_LOG, "onReceive");

            Log.i(TAG_LOG,  MyLocation.getInstance().getLocation().toString());

            txtView.setText(MyLocation.getInstance().getLocation().toString());

        }
    }
}
