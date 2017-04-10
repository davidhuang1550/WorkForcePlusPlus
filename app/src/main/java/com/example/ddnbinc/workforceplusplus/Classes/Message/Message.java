package com.example.ddnbinc.workforceplusplus.Classes.Message;

/**
 * Created by davidhuang on 2017-03-11.
 */

public abstract class Message {

    private String SenderID;
    private String SenderName;
    private Long Timestamp;

    public Message(){

    }
    public Message(String SenderId, Long Timestamp,String SenderName){
        this.SenderID=SenderId;
        this.Timestamp= Timestamp;
        this.SenderName = SenderName;
    }
    public Long getTimestamp() {
        return Timestamp;
    }

    public void setTimestamp(Long timestamp) {
        Timestamp = timestamp;
    }

    public String getSenderID() {
        return SenderID;
    }

    public void setSenderID(String sender) {
        SenderID = sender;
    }
    public abstract boolean getType();
    public String getSenderName() {
        return SenderName;
    }

    public void setSenderName(String senderName) {
        SenderName = senderName;
    }
}

