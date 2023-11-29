package com.example.calculatricev2.presenter;

import android.content.Context;

import com.example.calculatricev2.model.RSSModel;
import com.example.calculatricev2.view.RSSView;

public class RSSPresenter {
    RSSModel _RSSModel;
    RSSView _RSSView;

    public RSSPresenter(Context context){
        _RSSModel = new RSSModel();
        _RSSModel.setUrl("https://www.lemonde.fr/rss/une.xml");
        _RSSModel.setContext(context);
        _RSSModel.processFeed();

    }

    public void setView(RSSView rssView) {
        _RSSView = rssView;
    }
}
