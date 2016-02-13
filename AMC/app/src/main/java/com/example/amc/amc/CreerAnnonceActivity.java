package com.example.amc.amc;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

public class CreerAnnonceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_creer_annonce);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
    }

    public void btnCreerAnnonce(View v)
    {
        EditText txt = (EditText) findViewById(R.id.editText);
        EditText txt1 = (EditText) findViewById(R.id.editText2);
        EditText txt2 = (EditText) findViewById(R.id.editText3);
        EditText txt3 = (EditText) findViewById(R.id.editText4);
        EditText txt4 = (EditText) findViewById(R.id.editText5);
        EditText txt5 = (EditText) findViewById(R.id.editText6);
        EditText txt6 = (EditText) findViewById(R.id.editText7);
        EditText txt7 = (EditText) findViewById(R.id.editText8);
        EditText txt8 = (EditText) findViewById(R.id.editText9);
        EditText txt9 = (EditText) findViewById(R.id.editText10);

        MainActivity.LstAnnonces.add(new Annonce(MainActivity.LstAnnonces.size() + 1, txt.getText().toString(),
                                                 txt1.getText().toString(), txt2.getText().toString(),
                                                 txt3.getText().toString(), txt4.getText().toString(),
                                                 txt5.getText().toString(), txt6.getText().toString(),
                                                 txt8.getText().toString(), txt7.getText().toString(),
                                                 txt9.getText().toString(),
                                                 LoginActivity.user));

        Intent AutreFiche = new Intent(this, VoirAnnoncesActivity.class);
        startActivity(AutreFiche);
    }


}
