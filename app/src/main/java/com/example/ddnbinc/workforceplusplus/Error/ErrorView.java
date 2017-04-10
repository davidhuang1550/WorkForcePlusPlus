package com.example.ddnbinc.workforceplusplus.Error;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-04-02.
 */

public class ErrorView extends Fragment {

    private Activity mActivity;
    private View mView;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

        Bundle bundle = getArguments();



        mView = inflater.inflate(R.layout.nothing_was_found, container, false);
        if(bundle !=null ) {
            String error = bundle.getString("ErrorName", null);
            if (error != null) {
                TextView textView = (TextView) mView.findViewById(R.id.error);

                textView.setText(error);
            }
        }
        return mView;
    }
}
