package com.example.amc.amc;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.io.IOException;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;

public class VoirAnnoncesActivity extends AppCompatActivity implements LocationListener, NavigationView.OnNavigationItemSelectedListener {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_voir_annonces);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        Button btn = (Button) findViewById(R.id.btnRechercher);

        setSupportActionBar(toolbar);

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.bringToFront();
        btn.bringToFront();
        if (LoginActivity.user != null)
        {
            navigationView.getMenu().getItem(0).setVisible(false);
        }
        else
        {
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
        }
    }

    public void AfficherLesAnnonces(ArrayList<Annonce> annonce) {
        ListView listview = (ListView) findViewById(R.id.listView);
        ArrayList<String> myStringArray1 = new ArrayList<String>();

        myStringArray1.add("Code | Titre | Ville |");
        for (int i = 0; i < annonce.size(); i++)
            myStringArray1.add(String.valueOf(annonce.get(i).ID) + " | " + annonce.get(i).Titre + " | " + annonce.get(i).Ville + " | Postuler");

        ArrayAdapter<String> adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, myStringArray1);
        listview.setAdapter(adapter);
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

    public void btnRechercherOnClick(View v) {
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

        try {
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
            EditText texteRecherche = (EditText) findViewById(R.id.editText);

            if (texteRecherche.getText().toString() != null && texteRecherche.getText().toString() != "") {

                List<Address> adresse = coder.getFromLocationName(texteRecherche.getText().toString(), 1);
                if (adresse != null && adresse.size() > 0) {
                    location.setLatitude(adresse.get(0).getLatitude());
                    location.setLongitude(adresse.get(0).getLongitude());
                } else {
                    final AlertDialog alertDialog = new AlertDialog.Builder(this).create();
                    alertDialog.setTitle("Erreur");
                    alertDialog.setMessage("L'application n'a pas pu trouvé une adresse correspondante à celle que vous avez entré.");
                    alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            alertDialog.cancel();
                        }
                    });
                    alertDialog.show();

                }
            }

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

            ArrayList<Annonce> lst = new ArrayList<Annonce>();

            for (int a = 0; a < MainActivity.LstAnnonces.size(); a++)
                lst.add(MainActivity.LstAnnonces.get(LstTrie[a]));

            AfficherLesAnnonces(lst);

        } catch (IOException e) {

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_connect) {
            Intent startbuttonintent = new Intent(this, LoginActivity.class);
            startActivity(startbuttonintent);

        } else if (id == R.id.nav_disconnect) {
            LoginActivity.user = null;
            Intent startbuttonintent = new Intent(this, VoirAnnoncesActivity.class);
            startActivity(startbuttonintent);

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_MAnnonces) {

        } else if (id == R.id.nav_LAnnonces) {
            Intent startbuttonintent = new Intent(this, VoirAnnoncesActivity.class);
            startActivity(startbuttonintent);
        }
        else if (id == R.id.nav_CreerAnnonce) {
            Intent startbuttonintent = new Intent(this, CreerAnnonceActivity.class);
            startActivity(startbuttonintent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
