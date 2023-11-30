package com.example.calculatricev2.model;

import static com.example.calculatricev2.presenter.RSSPresenter.showRSSContent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class RSSHandler extends DefaultHandler {
    private boolean inTitle = false;
    private boolean inDescription = false;
    private boolean inItem = false;
    private boolean inDate = false;
    private Bitmap image = null;
    private String imageURL = null;
    // Le titre, la description et la date extraits du flux RSS
    private StringBuffer title = new StringBuffer();
    private StringBuffer description = new StringBuffer();
    private StringBuffer date = new StringBuffer();
    private int _numItem; // Le numéro de l'item à extraire du flux RSS
    private int currentItem = 0;

    public RSSHandler(Integer numItem){
        _numItem = numItem;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("item")) {
            inItem = true;
            currentItem++;
        } else if (inItem) {
            if (currentItem == _numItem) {
                if (qName.equalsIgnoreCase("title")) {
                    inTitle = true;
                    title.setLength(0);
                } else if (qName.equalsIgnoreCase("description")) {
                    inDescription = true;
                    description.setLength(0);
                } else if (qName.equalsIgnoreCase("pubDate")) {
                    inDate = true;
                    date.setLength(0);
                } else if (qName.equalsIgnoreCase("media:content")||qName.equalsIgnoreCase("enclosure")) {
                    imageURL = attributes.getValue("url");

                    try {
                        image = getBitmap(imageURL);
                        showRSSContent(image, date.toString(), title.toString(), description.toString());
                    } catch (Exception e) {
                        System.out.println("processFeed Exception" + e.getMessage());
                    }

                    inItem = false;
                }
            }
        }
    }

    public void characters(char ch[], int start, int length) {
        String chars = new String(ch, start, length);
        if (inTitle) {
            title.append(chars);
            inTitle = false;
        } else if (inDescription) {
            description.append(chars);
            inDescription = false;
        } else if (inDate) {
            date.append(chars);
            inDate = false;
        }
    }


    public Bitmap getBitmap(String imageURL) {
        try {
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
}