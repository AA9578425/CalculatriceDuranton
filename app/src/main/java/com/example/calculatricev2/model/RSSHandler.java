package com.example.calculatricev2.model;

import static com.example.calculatricev2.presenter.RSSPresenter.showRSSContent;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class RSSHandler extends DefaultHandler {
    private boolean inTitle = false;
    private boolean inDescription = false;
    private boolean inItem = false;
    private boolean inDate = false;
    private final StringBuffer title = new StringBuffer();
    private final StringBuffer description = new StringBuffer();
    private final StringBuffer date = new StringBuffer();
    private final int _numItem; // Le numéro de l'item à extraire du flux RSS
    private int currentItem = 0;
    private int maxItem;

    public RSSHandler(Integer numItem){
        _numItem = numItem;
    }

    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        if (qName.equalsIgnoreCase("item")) {
            inItem = true;
            currentItem++;
            maxItem = currentItem;
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
                    String imageURL = attributes.getValue("url");

                    try {
                        Bitmap image = getBitmap(imageURL);
                        showRSSContent(image, date.toString(), title.toString(), description.toString());
                    } catch (Exception e) {
                        System.out.println("processFeed Exception" + e.getMessage());
                    }

                    inItem = false;
                }
            }
        }
    }

    public void characters(char[] ch, int start, int length) {
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
            return new DownloadImageThread().downloadImage(imageURL).get();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getItemNumber() {
        return maxItem;
    }

    public static class DownloadImageThread {

        public Future<Bitmap> downloadImage(String imageURL) {
            ExecutorService executorService = Executors.newSingleThreadExecutor();
            return executorService.submit(new DownloadImageCallable(imageURL));
        }

        private static class DownloadImageCallable implements Callable<Bitmap> {
            private final String _imageURL;
            public DownloadImageCallable(String imageURL) {
                _imageURL = imageURL;
            }
            @Override
            public Bitmap call() {
                try {
                    HttpURLConnection connection = (HttpURLConnection) new URL(_imageURL).openConnection();
                    return BitmapFactory.decodeStream(connection.getInputStream());
                } catch (IOException e) {
                    e.printStackTrace();
                    return null;
                }
            }
        }
    }
}