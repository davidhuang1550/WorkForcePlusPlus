package com.example.ddnbinc.workforceplusplus.ChatRoom;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.ddnbinc.workforceplusplus.MainActivity;
import com.example.ddnbinc.workforceplusplus.R;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class ChatRoom extends Fragment implements View.OnClickListener{

    private Activity mActivity;
    private RecyclerView recyclerView;
    private View mView;
    private ChatRoomPresenter chatRoomPresenter;
    private EditText messageLog;
    private Button Send;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.chat_box,container,false);
        ((MainActivity)mActivity).setTitle("Chat Room");
        recyclerView = (RecyclerView)mView.findViewById(R.id.recylerview);

        chatRoomPresenter = new ChatRoomPresenter(mActivity,recyclerView);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(mActivity);
        recyclerView.setLayoutManager(mLayoutManager);

        Send = (Button)mView.findViewById(R.id.send);
        messageLog = (EditText)mView.findViewById(R.id.message);

        Send.setOnClickListener(this);

        chatRoomPresenter.fetchMessages();

        return mView;
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.send :
                String message = messageLog.getText().toString();
                if(message.length()!=0){
                    chatRoomPresenter.SendMessage(message);
                    messageLog.setText("");
                }

        }

    }
}
