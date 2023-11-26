package com.example.calculatricev2.presenter;

import com.example.calculatricev2.model.RSSModel;
import com.example.calculatricev2.view.RSSView;

public class RSSPresenter {
    RSSModel _RSSModel;
    RSSView _RSSView;

    public RSSPresenter(){
        _RSSModel = new RSSModel();
    }

    public void setView(RSSView rssView) {
        _RSSView = rssView;
    }
}
