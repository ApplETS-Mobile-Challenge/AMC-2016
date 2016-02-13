package com.example.amc.amc;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static Compte user = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (user != null)
        {
            Intent startbuttonintent = new Intent(this, VoirAnnoncesActivity.class);
            startActivity(startbuttonintent);
        }
        else
        {
            setContentView(R.layout.activity_login);
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            setSupportActionBar(toolbar);

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                    this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
            drawer.setDrawerListener(toggle);
            toggle.syncState();

            NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
            navigationView.setNavigationItemSelectedListener(this);

            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(1).setVisible(false);
            navigationView.getMenu().getItem(2).setVisible(false);
            navigationView.getMenu().getItem(3).setVisible(false);
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        MenuItem item1 = menu.findItem(R.id.nav_connect);

        MenuItem item2 = menu.findItem(R.id.nav_disconnect);
        MenuItem item3 = menu.findItem(R.id.nav_MAnnonces);
        MenuItem item4 = menu.findItem(R.id.nav_LAnnonces);
  /*      if (user != null)
        {
            item1.setVisible(false);
            item2.setVisible(true);
            item3.setVisible(true);
            item4.setVisible(true);
        }
        else
        {
            item1.setVisible(true);
            item2.setVisible(false);
            item3.setVisible(false);
            item4.setVisible(false);
        }*/



        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void OnClick(View v) {
        EditText edtUsername = (EditText) findViewById(R.id.NomUtilisateur);
        EditText edtPassword = (EditText) findViewById(R.id.MotPasse);

        if (edtUsername.getText().toString() != "" && edtPassword.getText().toString() != "")
            user = MainActivity.RetournerCompte(edtUsername.getText().toString(), edtPassword.getText().toString(), this);

        if (user != null)
        {
            Intent AutreFiche = new Intent(this, VoirAnnoncesActivity.class);
            startActivity(AutreFiche);
        }
        else
        {
            AlertDialog alertDialog = new AlertDialog.Builder(this).create();
            alertDialog.setTitle("Connexion");
            alertDialog.setMessage("Mot de passe invalide!");
            alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        }
    }
}
