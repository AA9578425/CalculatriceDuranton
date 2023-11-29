package com.example.calculatricev2.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RSSModel extends DefaultHandler {
    private Context context;
    private String _url = "https://www.lemonde.fr/rss/une.xml";// l'URL du flux RSS à parser
    // Ensemble de drapeau permettant de savoir où en est le parseur dans le flux XML
    private boolean inTitle = false;
    private boolean inDescription = false;
    private boolean inItem = false;
    private boolean inDate = false;
    // L'image référencée par l'attribut url du tag <enclosure>
    private Bitmap image = null;
    private String imageURL = null;
    // Le titre, la description et la date extraits du flux RSS
    private StringBuffer title = new StringBuffer();
    private StringBuffer description = new StringBuffer();
    private StringBuffer date = new StringBuffer();
    private int numItem = 0; // Le numéro de l'item à extraire du flux RSS
    private int numItemMax = -1; // Le nombre total d’items dans le flux RSS

    public void setUrl(String url) {
        _url = url;
    }
    public void setContext(Context context){
        this.context = context;
    }

    public void processFeed() {
        System.out.println("A");
        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, _url,
                response -> {
                    System.out.println("Réponse : " + response);
                },
                error -> {
                    System.out.println("Erreur : " + error.toString());
                });
        System.out.println("B");
        Volley.newRequestQueue(context).add(stringRequest2);
        System.out.println("C");
        /*
        try {
            image = getBitmap(imageURL);
            System.out.println(image);
            numItemMax = numItem;
        } catch (Exception e) {
            Log.e("smb116rssview", "processFeed Exception" + e.getMessage());
        }*/
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        // Cette méthode est appelée au début de chaque élément du flux RSS.
        // Vous devez ajouter le code pour gérer chaque type d'élément du flux RSS.
        System.out.println("startElement");
        if (qName.equalsIgnoreCase("item")) {
            // Début d'un nouvel élément <item>
            inItem = true;
        } else if (inItem) {
            // Si nous sommes à l'intérieur d'un élément <item>, nous vérifions quel élément spécifique commence.
            if (qName.equalsIgnoreCase("title")) {
                inTitle = true;
                title.setLength(0); // Réinitialiser le StringBuffer du titre.
            } else if (qName.equalsIgnoreCase("description")) {
                inDescription = true;
                description.setLength(0); // Réinitialiser le StringBuffer de la description.
            } else if (qName.equalsIgnoreCase("pubDate")) {
                inDate = true;
                date.setLength(0); // Réinitialiser le StringBuffer de la date.
            } else if (qName.equalsIgnoreCase("enclosure")) {
                // Si l'élément est <enclosure>, récupérer l'URL de l'image.
                imageURL = attributes.getValue("url");
            }
        }
    }

    public void characters(char ch[], int start, int length) {
        // Cette méthode est appelée lorsque des caractères sont présents à l'intérieur d'un élément.
        System.out.println("Characters");
        String chars = new String(ch).substring(start, start + length);
        if (inTitle) {
            title.append(chars);
        } else if (inDescription) {
            description.append(chars);
        } else if (inDate) {
            date.append(chars);
        }
    }


    public Bitmap getBitmap(String imageURL) {
        try {
            // Utilisez AsyncTask pour effectuer l'opération de réseau de manière asynchrone
            return new DownloadImageTask().execute(imageURL).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static class DownloadImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected Bitmap doInBackground(String... urls) {
            String imageURL = urls[0];
            try {
                URL url = new URL(imageURL);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                return BitmapFactory.decodeStream(input);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
    }



/*
    private static RssReader instance;
    private RequestQueue requestQueue;
    private static Context context;

    private RssReader(Context ctx) {
        context = ctx;
        requestQueue = getRequestQueue();
    }

    public static synchronized RssReader getInstance(Context context) {
        if (instance == null) {
            instance = new RssReader(context);
        }
        return instance;
    }

    public RequestQueue getRequestQueue() {
        if (requestQueue == null) {
            requestQueue = Volley.newRequestQueue(context.getApplicationContext());
        }
        return requestQueue;
    }

    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }

    public void fetchRss(String url, final RssCallback callback) {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        callback.onSuccess(response);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        callback.onError(error.getMessage());
                    }
                });

        addToRequestQueue(stringRequest);
    }

    public interface RssCallback {
        void onSuccess(String rssResponse);
        void onError(String errorMessage);
    }*/
}