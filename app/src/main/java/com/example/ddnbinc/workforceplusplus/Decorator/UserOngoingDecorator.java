package com.example.ddnbinc.workforceplusplus.Decorator;

import android.view.Gravity;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class UserOngoingDecorator implements TextDecorator {
    @Override
    public void draw(TableLayout textView) {
        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT);

        textView.setPadding(50,20,80,20);
        layoutParams.gravity = Gravity.RIGHT;
        textView.setBackgroundResource(R.drawable.user_speech_bubble_ongoing);
        textView.setLayoutParams(layoutParams);

    }
}
