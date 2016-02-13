package com.example.amc.amc;

/**
 * Created by Jonathan on 2016-02-13.
 */
public class Compte {
    public int ID;
    public int Age;

    public String Prenom;
    public String Nom;
    public String Courriel;
    public String MotDePasse;
    public String NoCivique;
    public String NomRue;
    public String CodePostal;
    public String Ville;

    public Compte(int _ID, int _Age, String _Prenom, String _Nom, String _Courriel, String _MotDePasse, String _NoCivique,
                  String _NomRue, String _CodePostal, String _Ville)
    {
        ID = _ID;
        Age = _Age;
        Prenom = _Prenom;
        Nom = _Nom;
        Courriel = _Courriel;
        MotDePasse = _MotDePasse;
        NoCivique = _NoCivique;
        NomRue = _NomRue;
        CodePostal = _CodePostal;
        Ville = _Ville;
    }
}
