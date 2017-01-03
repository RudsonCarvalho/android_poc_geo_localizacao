package geolocalizacao.poc.com.br.poc_geolocalizacao;

import android.location.Location;

/**
 * Created by rksc on 29/12/16.
 */
public class MyLocation {

    private MyLocation() {}

    private static MyLocation myLocation;

    public static MyLocation getInstance() {

        if (myLocation == null) {
            myLocation = new MyLocation();
        }

        return myLocation;
    }

    private volatile Location _location;

    public Location getLocation () {
        return _location;
    }

    public void setLocation(Location location) {

        _location = location;
    }
}
