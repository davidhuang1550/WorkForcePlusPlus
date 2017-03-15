package com.example.ddnbinc.workforceplusplus.ChatRoom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class ChatRoomPresenter {
    private ChatRoomManager chatRoomManager;


    public ChatRoomPresenter(Activity activity, RecyclerView recyclerView){
        chatRoomManager = new ChatRoomManager(activity,recyclerView);
    }

    public void fetchMessages(){
        chatRoomManager.FetchMessages();
    }

    public boolean SendMessage(String message){
        return chatRoomManager.SendMessage(message);
    }

    public void forceScrollToBottom(){
        chatRoomManager.forceScrollToBottom();
    }
}
