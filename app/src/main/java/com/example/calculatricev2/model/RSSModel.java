package com.example.calculatricev2.model;

import android.graphics.Bitmap;

public class RSSModel {
    private String url = null ;// l'URL du flux RSS à parser
    // Ensemble de drapeau permettant de savoir où en est le parseur dans le flux XML
    private boolean inTitle = false ;
    private boolean inDescription = false ;
    private boolean inItem = false ;
    private boolean inDate = false ;
    // L'image référencée par l'attribut url du tag <enclosure>
    private Bitmap image = null ;
    private String imageURL = null ;
    // Le titre, la description et la date extraits du flux RSS
    private StringBuffer title = new StringBuffer();
    private StringBuffer description = new StringBuffer();
    private StringBuffer date = new StringBuffer();
    private int numItem = 0; // Le numéro de l'item à extraire du flux RSS
    private int numItemMax = - 1; // Le nombre total d’items dans le flux RSS
}
