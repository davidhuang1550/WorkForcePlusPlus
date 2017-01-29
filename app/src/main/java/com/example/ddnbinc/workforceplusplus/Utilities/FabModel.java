package com.example.ddnbinc.workforceplusplus.Utilities;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import com.example.ddnbinc.workforceplusplus.Fragments.Shift.ViewShift.ViewShift;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-01-28.
 */

public class FabModel {
    private FloatingActionButton fab;
    private Activity mActivity;
    public FabModel(FloatingActionButton f, final Activity activity){
        fab=f;
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle bundle = new Bundle();
                bundle.putString("GiveUpShift","yes");

                ViewShift viewShift = new ViewShift();
                viewShift.setArguments(bundle);

               /* FragmentTransaction transaction = ((MainActivity) activity).getFragmentManager().beginTransaction();
                transaction.setCustomAnimations(R.animator.enter_anim, R.animator.exit_anim, R.animator.enter_anim_back, R.animator.exit_anime_back);
                transaction.add(R.id.content_frame, viewShift).addToBackStack("Shifts").commit();

                */
                FragmentManager fragmentManager = ((MainActivity)activity).getFragmentManager();
                fragmentManager.beginTransaction().replace(R.id.content_frame,new ViewShift()).commit();
            }
        });

    }

    public void Show(){
        fab.show();
    }
    public void Hide(){
        fab.hide();
    }
    /*
      Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
     */
}
