package com.example.ddnbinc.workforceplusplus.Classes;

import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.ddnbinc.workforceplusplus.R;

import java.security.Timestamp;

/**
 * Created by davidhuang on 2017-03-05.
 */

public class Notification {
    private String Title;
    private String Message;
    private Long Timestamp;
    private String type;


    public Notification(){

    }

    public Notification(String t, String m, Long time, String ptype){
        Title=t;
        Message=m;
        Timestamp=time;
        type=ptype;

    }
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }
    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }

    public void setView(ImageView view){
        // 127w,95h
        view.getLayoutParams().height=200;
        view.getLayoutParams().width=300;
        switch (type){
            case "TradePending":
                view.setBackgroundResource(R.drawable.pending_shift);
                break;
            case "TradeSucessful":
                view.setBackgroundResource(R.drawable.shift_approved);
                break;
            case "TradeUnsucessful":
                view.setBackgroundResource(R.drawable.shift_denied);
                break;
            case "Message":
                view.setBackgroundResource(R.drawable.mail);
                break;
            case "Urgent":
                view.setBackgroundResource(R.drawable.urgent);
                break;
        }

    }

}
