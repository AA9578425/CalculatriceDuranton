package com.example.calculatricev2.model;

import static android.provider.MediaStore.Images.Media.getBitmap;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;

import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.Locator;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class RSSModel implements ContentHandler {
    private String url = null;// l'URL du flux RSS à parser
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
        this.url = url;
    }


    public void processFeed() {
        try {
            numItem = 0;
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            XMLReader reader = parser.getXMLReader();
            reader.setContentHandler(this);
            InputStream inputStream = new URL(url).openStream();
            reader.parse(new InputSource(inputStream));
            image = getBitmap(imageURL);
            System.out.println(image);
            numItemMax = numItem;
        } catch (Exception e) {
            Log.e("smb116rssview", "processFeed Exception" + e.getMessage());
        }
    }

    @Override
    public void setDocumentLocator(Locator locator) {

    }

    @Override
    public void startDocument() throws SAXException {

    }

    @Override
    public void endDocument() throws SAXException {

    }

    @Override
    public void startPrefixMapping(String prefix, String uri) throws SAXException {

    }

    @Override
    public void endPrefixMapping(String prefix) throws SAXException {

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

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {

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

    @Override
    public void ignorableWhitespace(char[] ch, int start, int length) throws SAXException {

    }

    @Override
    public void processingInstruction(String target, String data) throws SAXException {

    }

    @Override
    public void skippedEntity(String name) throws SAXException {

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
}