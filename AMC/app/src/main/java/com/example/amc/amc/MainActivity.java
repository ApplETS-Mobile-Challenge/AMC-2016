package com.example.amc.amc;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    public static ArrayList<Compte> LstComptes;
    public static ArrayList<Annonce> LstAnnonces;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        LstComptes = new ArrayList<Compte>();
        LstAnnonces = new ArrayList<Annonce>();

        LstComptes.add(new Compte(1, 12, "Samuel", "Nadeau", "enfantduperenoel@hotmail.com", "qwerty", "20", "Richard", "J0K1K0", "Lourdes-de-Joliette"));
        LstComptes.add(new Compte(2, 18, "Jonathan", "Clavet-Grenier", "jesuismajeur@hotmail.com", "123456", "1111", "Rang Montcalm", "J0K2XO", "St-Liguori"));
        LstComptes.add(new Compte(3, 20, "Madeline", "Poulin", "ets@hotmail.com", "654321", "1111", "Rue Notre-Dame Ouest", "H3C6M8", "Montréal"));

        LstAnnonces.add(new Annonce(1, "Évènement AMC 2016", "Nous avons besoin de bénévolats pour l'évènement AMC 2016. Ces bénévolats devront effectuer des tâches simples telles que prendre les manteaux des personnes, accueillir les gens, etc. Nourriture et hôtel fournit gratuitement. Appelez pour avoir plus d'informations. 18 ans et plus requis.",
                "18 février 2016", "18h00", "19 février 2016", "16h00", "1111", "Rue Notre-Dame Ouest", "H3C6M8", "Montréal", LstComptes.get(2)));
        LstAnnonces.add(new Annonce(2, "Tracteur à gazon", "Nous cherchons quelqu'un en mesure de couper le gazon. Mon grand-père perd de plus en plus son autonomie et n'est malheureusement plus apte à le faire par lui-même.",
                "Indéfini", "Indéfini", "Indéfini", "Indéfini", "20", "Richard", "J0K1K0", "Lourdes-de-Joliette", LstComptes.get(1)));
        LstAnnonces.get(1).Demandeurs.add(LstComptes.get(0));
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
            // Handle the camera action
        } else if (id == R.id.nav_deconnect) {

        } else if (id == R.id.nav_info) {

        } else if (id == R.id.nav_MAnnonces) {

        } else if (id == R.id.nav_LAnnonces) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public static Compte RetournerCompte(String _Courriel, String _MotDePasse)
    {
        int IndCompte;

        IndCompte = 0;

        while (IndCompte < LstComptes.size() && LstComptes.get(IndCompte).Courriel != _Courriel)
            IndCompte++;

        if (IndCompte < LstComptes.size() && LstComptes.get(IndCompte).MotDePasse == _MotDePasse)
            return LstComptes.get(IndCompte);

        return null;
    }

    public static Annonce RetournerAnnonce(int _ID)
    {
        int IndAnnonce;

        IndAnnonce = 0;

        while (IndAnnonce < LstAnnonces.size() && LstAnnonces.get(IndAnnonce).ID != _ID)
            IndAnnonce++;

        if (IndAnnonce < LstAnnonces.size())
            return LstAnnonces.get(IndAnnonce);

        return null;
    }

    public static ArrayList<Annonce> RetournerToutesLesAnnoncesDunCompte(Compte _Compte)
    {
        int IndAnnonce;

        ArrayList<Annonce> LstAnnoncesDuCompte = new ArrayList<Annonce>();

        IndAnnonce = 0;

        while (IndAnnonce < LstAnnonces.size())
        {
            if (LstAnnonces.get(IndAnnonce).Createur.ID == _Compte.ID)
                LstAnnoncesDuCompte.add(LstAnnonces.get(IndAnnonce));
            IndAnnonce++;
        }

        return LstAnnoncesDuCompte;
    }
}
