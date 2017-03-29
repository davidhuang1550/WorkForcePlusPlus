package com.example.ddnbinc.workforceplusplus.ChatRoom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;

import com.example.ddnbinc.workforceplusplus.Classes.Message.Message;

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


    public void forceScrollToBottom(){
        chatRoomManager.forceScrollToBottom();
    }
}
