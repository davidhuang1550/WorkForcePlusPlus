package com.example.ddnbinc.workforceplusplus.Decorator;

import android.widget.TableLayout;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class OtherOngoingDecorator implements TextDecorator {
    @Override
    public void draw(TableLayout textView) {
        textView.setPadding(80,20,50,20);
        textView.setBackgroundResource(R.drawable.speech_bubble_ongoing);
    }
}
