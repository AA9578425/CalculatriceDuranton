package com.example.calculatricev2.model;

import static com.example.calculatricev2.presenter.RSSPresenter.showRSSContent;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RSSModel {
    private Context context;
    private String _url = "https://www.lemonde.fr/rss/une.xml";

    private int numItem = 1; // Le numéro de l'item à extraire du flux RSS

    public void setUrl(String url) {
        _url = url;
    }
    public void setContext(Context context){
        this.context = context;
    }

    public void processFeed() {

        StringRequest stringRequest2 = new StringRequest(Request.Method.GET, _url,
                response -> {
                    try {

                        SAXParserFactory factory = SAXParserFactory.newInstance();

                        SAXParser saxParser = factory.newSAXParser();

                        RSSHandler handler = new RSSHandler(numItem);

                        saxParser.parse(new InputSource(new StringReader(response)), handler);

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                },
                error -> System.out.println("Erreur : " + error.toString()));

        Volley.newRequestQueue(context).add(stringRequest2);
    }
    public void nextItem(){
        numItem++;
    }

    public void previousItem(){
        if(numItem > 0) {
            numItem--;
        }
    }
}