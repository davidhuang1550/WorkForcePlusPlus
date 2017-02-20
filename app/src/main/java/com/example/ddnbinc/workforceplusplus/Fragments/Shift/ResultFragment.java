package com.example.ddnbinc.workforceplusplus.Fragments.Shift;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-02-09.
 */

public class ResultFragment extends Fragment implements  View.OnClickListener {
    private View myView;
    private Activity mActivity;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        myView = inflater.inflate(R.layout.result_fragment,container,false);
        Bundle bundle =getArguments();
        if(bundle!=null){
            init(bundle);
        }



        return myView;
    }
    public void init(Bundle b){
        TextView time = (TextView)myView.findViewById(R.id.time);
        TextView response = (TextView)myView.findViewById(R.id.response);
        time.setText("Shift at" + b.getString("Time"));
        response.setText("Has Been" + b.getString("Response"));
    }

    @Override
    public void onClick(View view) {
        if(view.getId()==R.id.OkButton){
            ((MainActivity) mActivity).onBackPressed();
        }
    }
}
