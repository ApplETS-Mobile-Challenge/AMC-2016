package com.example.amc.amc;

import java.util.ArrayList;

/**
 * Created by Jonathan on 2016-02-13.
 */
public class Annonce {
    public int ID;

    public String Titre;
    public String Description;
    public String DateDebutEvenement;
    public String HeureDebutEvenement;
    public String DateFinEvenement;
    public String HeureFinEvenement;
    public String NoCivique;
    public String NomRue;
    public String CodePostal;
    public String Ville;

    public boolean MajeurSeulement;

    public Compte Createur;
    public ArrayList<Compte> Demandeurs;

    public Annonce(int _ID, String _Titre, String _Description, String _DateDebutEvenement, String _HeureDebutEvenement,
                   String _DateFinEvenement, String _HeureFinEvenement, String _NoCivique, String _NomRue, String _CodePostal, String _Ville,
                   Compte _Createur)
    {
        ID = _ID;
        Titre = _Titre;
        Description = _Description;
        DateDebutEvenement = _DateDebutEvenement;
        HeureDebutEvenement = _HeureDebutEvenement;
        DateFinEvenement = _DateFinEvenement;
        HeureFinEvenement = _HeureFinEvenement;
        NoCivique = _NoCivique;
        NomRue = _NomRue;
        CodePostal = _CodePostal;
        Ville = _Ville;
        Createur = _Createur;
        Demandeurs = new ArrayList<Compte>();
    }
}
