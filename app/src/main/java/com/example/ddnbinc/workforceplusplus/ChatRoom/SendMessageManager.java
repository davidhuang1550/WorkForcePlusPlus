package com.example.ddnbinc.workforceplusplus.ChatRoom;

import com.example.ddnbinc.workforceplusplus.Classes.Message.Message;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;

/**
 * Created by davidhuang on 2017-03-15.
 */

public class SendMessageManager {

    public static boolean SendMessage(Message message,DataBaseConnectionPresenter dataBaseConnectionPresenter){
        try {
            // Message
            DatabaseReference referece = dataBaseConnectionPresenter.getDbReference().child("ChatRoom").push();

            referece.setValue(message);
        }catch (DatabaseException e){
            return false;
        }
        return true;
    }
}
