package com.example.ddnbinc.workforceplusplus.Manager;

import android.app.Activity;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.View;
import android.widget.ListView;

/**
 * Created by davidhuang on 2017-02-19.
 */

public class PendingShiftsPresenter {
    private PendingShiftsModel pendingShiftsModel;

    public PendingShiftsPresenter(Activity activity, ListView listView, SwipeRefreshLayout swipeRefreshLayout,View view){
        pendingShiftsModel = new PendingShiftsModel(activity,listView,swipeRefreshLayout,view);
    }
    public void fetchShifts(){
        pendingShiftsModel.fetchShifts();
    }
}
