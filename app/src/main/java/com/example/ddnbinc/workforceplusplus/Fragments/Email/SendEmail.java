package com.example.ddnbinc.workforceplusplus.Fragments.Email;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-02-18.
 */

public class SendEmail extends Fragment {
    private Activity mActivity;
    private View myView;
    private EmailPresenter emailPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity=getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mActivity.setTitle("Send Email");
        myView = inflater.inflate(R.layout.email_list,container,false);
        SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout)myView.findViewById(R.id.swiperefresh);
        ListView listView = (ListView)myView.findViewById(R.id.listview);
        emailPresenter = new EmailPresenter(mActivity,listView,swipeRefreshLayout);
        swipeRefreshLayout.setRefreshing(true);
        init();

        return myView;
    }
    public void init(){
        emailPresenter.GetEmail();
    }
}
