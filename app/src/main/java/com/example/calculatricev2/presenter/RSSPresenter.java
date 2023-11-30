package com.example.calculatricev2.presenter;

import android.content.Context;
import android.graphics.Bitmap;

import com.example.calculatricev2.model.RSSModel;
import com.example.calculatricev2.view.RSSView;

public class RSSPresenter {
    RSSModel _RSSModel;
    static RSSView _RSSView;

    public RSSPresenter(Context context){
        _RSSModel = new RSSModel();
        _RSSModel.setUrl("https://zapier.com/engine/rss/17211843/ENSICAEN"); // https://www.lemonde.fr/rss/une.xml
        _RSSModel.setContext(context);
        _RSSModel.processFeed();

    }

    public void setView(RSSView rssView) {
        _RSSView = rssView;
    }

    public static void showRSSContent(Bitmap bitmap, String date, String title, String description){
        _RSSView.showRSSContent(bitmap, date, title, description);
    }

    public void previousItem() {
        _RSSModel.previousItem();
        _RSSModel.processFeed();
    }

    public void nextItem() {
        _RSSModel.nextItem();
        _RSSModel.processFeed();
    }
}
