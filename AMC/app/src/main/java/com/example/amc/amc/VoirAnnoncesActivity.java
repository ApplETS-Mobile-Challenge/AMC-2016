package com.example.amc.amc;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

public class VoirAnnoncesActivity extends AppCompatActivity implements LocationListener {

    private double Latitude;
    private double Longitude;
    private double Altitude;
    private float Accuracy;
    private boolean GpsActive;
    private boolean ConnexionReseauActive;
    private boolean CanGetLocation;

    private Location location;
    private Geocoder coder;

    public static ArrayList<Address> Adresses;

    // The minimum distance to change Updates in meters
    private static final long MIN_DISTANCE_CHANGE_FOR_UPDATES = 10; // 10 meters

    // The minimum time between updates in milliseconds
    private static final long MIN_TIME_BW_UPDATES = 1000 * 60 * 1; // 1 minute

    public static ArrayList<Geocoder> LstLocations;
    public static int[] LstTrie;

    LocationManager LocationManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voir_annonces);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        LocationManager = (LocationManager) this.getSystemService(LOCATION_SERVICE);
        if (LocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return;
            } else
                LocationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10000, 0, this);
        }
        LocationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 10000, 0, this);
    }

    @Override
    protected void onPause() {
        super.onPause();  // Always call the superclass method first

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        } else
            LocationManager.removeUpdates(this);
    }

    public void btnRechercherOnClick(View v)  {
        LocationManager = (LocationManager) this.getSystemService(this.LOCATION_SERVICE);

        // Obtenir le status du gps
        GpsActive = LocationManager
                .isProviderEnabled(LocationManager.GPS_PROVIDER);

        // Obtenir le status de la connection
        ConnexionReseauActive = LocationManager
                .isProviderEnabled(LocationManager.NETWORK_PROVIDER);

        if (!GpsActive && !ConnexionReseauActive) {
            // Si il n'y a pas de connection ou de gps
        } else {
            this.CanGetLocation = true;
            // Obtenir la location par le ISP
            if (ConnexionReseauActive) {
                LocationManager.requestLocationUpdates(
                        LocationManager.NETWORK_PROVIDER,
                        MIN_TIME_BW_UPDATES,
                        MIN_DISTANCE_CHANGE_FOR_UPDATES, this);
                if (LocationManager != null) {
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    } else
                        location = LocationManager
                                .getLastKnownLocation(LocationManager.NETWORK_PROVIDER);

                    if (location != null) {
                        Latitude = location.getLatitude();
                        Longitude = location.getLongitude();
                    }
                }
            }
            // Obtenir la location par le service gps
            if (GpsActive) {
                if (location == null) {
                    LocationManager.requestLocationUpdates(
                            LocationManager.GPS_PROVIDER,
                            MIN_TIME_BW_UPDATES,
                            MIN_DISTANCE_CHANGE_FOR_UPDATES, this);

                    if (LocationManager != null) {
                        location = LocationManager
                                .getLastKnownLocation(LocationManager.GPS_PROVIDER);
                        if (location != null) {
                            Latitude = location.getLatitude();
                            Longitude = location.getLongitude();
                        }
                    }
                }
            }
        }

        try
        {
            coder = new Geocoder(this);
            ArrayList<Address> t = new ArrayList<Address>();

            for (int i = 0; i < MainActivity.LstAnnonces.size(); i++) {
                t.add(coder.getFromLocationName(MainActivity.LstAnnonces.get(i).NoCivique + " " + MainActivity.LstAnnonces.get(i).NomRue + ", " + MainActivity.LstAnnonces.get(i).Ville + ", " +
                                               MainActivity.LstAnnonces.get(i).CodePostal, 1).get(0));
            }

            LstTrie = new int[t.size()];
            for (int i = 0; i < t.size(); i++) {
                LstTrie[i] = i;
            }

            String test = "";

            for (int i = 0; i < MainActivity.LstAnnonces.size() - 1; i++) {
                for (int j = 0; j < MainActivity.LstAnnonces.size(); j++) {
                    Location locI = new Location("B");
                    locI.setLatitude(t.get(LstTrie[i]).getLatitude());
                    locI.setLongitude(t.get(LstTrie[i]).getLongitude());

                    Location locJ = new Location("C");
                    locJ.setLatitude(t.get(LstTrie[j]).getLatitude());
                    locJ.setLongitude(t.get(LstTrie[j]).getLongitude());

                    if (location.distanceTo(locI) > location.distanceTo(locJ)) {
                        int indice = LstTrie[i];
                        LstTrie[i] = LstTrie[j];
                        LstTrie[j] = indice;
                    }
                }
            }

            for (int k = 0; k < MainActivity.LstAnnonces.size(); k++) {
                test = test + t.get(LstTrie[k]).getAddressLine(0) + ';';
            }

            Toast.makeText(this, test, Toast.LENGTH_SHORT).show();
        }
        catch (IOException e)
        {

        }



    }

    @Override
    public void onLocationChanged(Location location) {
        Latitude = location.getLatitude();
        Longitude = location.getLongitude();
        Altitude = location.getAltitude();
        Accuracy = location.getAccuracy();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
