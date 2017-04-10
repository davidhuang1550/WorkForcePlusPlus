package com.example.ddnbinc.workforceplusplus.ChatRoom;

import android.app.Activity;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.example.ddnbinc.workforceplusplus.Adapters.MessageAdapter;
import com.example.ddnbinc.workforceplusplus.Classes.Message.ImageMessage;
import com.example.ddnbinc.workforceplusplus.Classes.Message.Message;
import com.example.ddnbinc.workforceplusplus.Classes.Message.TextMessage;
import com.example.ddnbinc.workforceplusplus.Classes.Users.Employee;
import com.example.ddnbinc.workforceplusplus.DataBaseConnection.DataBaseConnectionPresenter;
import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseException;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class ChatRoomManager {

    private Activity mActivity;
    private RecyclerView recyclerView;
    private ArrayList<Message> messages;
    private DataBaseConnectionPresenter dataBaseConnectionPresenter;
    private MessageAdapter messageAdapter;
    private Employee employee;
    private boolean firstIteration;

    public ChatRoomManager(Activity mActivity,RecyclerView recyclerView){
        this.mActivity = mActivity;
        this.recyclerView = recyclerView;
        this.dataBaseConnectionPresenter = ((MainActivity)mActivity).getDataBaseConnectionPresenter();
        this.messages = new ArrayList<>();
        this.employee = ((MainActivity)mActivity).getEmployee();
        this.firstIteration=false;
        setAdapter();
    }

    public void FetchMessages(){
        dataBaseConnectionPresenter.getDbReference().child("ChatRoom").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if(firstIteration){
                    Message message;
                    if(dataSnapshot.hasChild("imageKey")){
                        message = dataSnapshot.getValue(ImageMessage.class);
                    }
                    else {
                        message = dataSnapshot.getValue(TextMessage.class);
                    }
                    messages.add(message);
                    messageAdapter.notifyDataSetChanged();
                    forceScrollToBottom();
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                // edit message we can do this later
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                // msg deleted alot like skype, can decide to do this later
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        dataBaseConnectionPresenter.getDbReference().child("ChatRoom").limitToLast(100).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(firstIteration==false) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Message message;
                        if(snapshot.hasChild("imageKey")){
                            message = snapshot.getValue(ImageMessage.class);
                        }
                        else {
                            message = snapshot.getValue(TextMessage.class);
                        }
                        messages.add(message);
                    }

                    firstIteration=true;
                    messageAdapter.notifyDataSetChanged();
                    forceScrollToBottom();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void forceScrollToBottom(){
        recyclerView.scrollToPosition(messages.size() - 1);
    }

    public void setAdapter(){
        messageAdapter = new MessageAdapter(messages,employee,mActivity);
        recyclerView.setAdapter(messageAdapter);
    }



}
