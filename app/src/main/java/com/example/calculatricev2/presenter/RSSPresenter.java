package com.example.calculatricev2.presenter;

import com.example.calculatricev2.model.RSS;
import com.example.calculatricev2.model.RSSModel;
import com.example.calculatricev2.view.RSSView;

public class RSSPresenter {
    RSS _RSSModel;
    RSSView _RSSView;

    public RSSPresenter(){
        _RSSModel = new RSS();
        //_RSSModel.setUrl("https://www.lemonde.fr/international/rss_full.xml");
       // _RSSModel.processFeed();
    }

    public void setView(RSSView rssView) {
        _RSSView = rssView;
    }
}
