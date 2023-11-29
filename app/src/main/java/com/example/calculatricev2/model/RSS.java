package com.example.calculatricev2.model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class RSS {

    public RSS() {
        System.out.println("AA");
        String rssFeedURL = "https://www.lemonde.fr/international/rss_full.xml";
        try {
            String rssContent = getRSSContent(rssFeedURL);
            parseRSS(rssContent);
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e.printStackTrace();
        }
    }

    private static String getRSSContent(String rssFeedURL) throws IOException {
        URL url = new URL(rssFeedURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder content = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            content.append(line);
        }

        reader.close();
        connection.disconnect();

        return content.toString();
    }

    private static void parseRSS(String rssContent) throws ParserConfigurationException, SAXException, IOException {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(rssContent);

        NodeList items = document.getElementsByTagName("item");

        for (int i = 0; i < items.getLength(); i++) {
            Node item = items.item(i);
            System.out.println(item.getNodeType());

            if (item.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) item;
                String title = element.getElementsByTagName("title").item(0).getTextContent();
                String description = element.getElementsByTagName("description").item(0).getTextContent();

                System.out.println("Title: " + title);
                System.out.println("Description: " + description);
                System.out.println("--------------------");
            }
        }
    }
}