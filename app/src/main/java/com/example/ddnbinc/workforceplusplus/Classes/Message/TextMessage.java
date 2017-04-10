package com.example.ddnbinc.workforceplusplus.Classes.Message;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class TextMessage extends Message {
    private String Message;

    public TextMessage(){

    }
    public TextMessage(String SenderID, Long Timestamp, String message,String SenderName){
        super(SenderID,Timestamp,SenderName);
        this.Message = message;
    }
    public String getMessage() {
        return Message;
    }

    public void setMessage(String message) {
        Message = message;
    }
    public Long getTimestamp() {
        return super.getTimestamp();
    }

    public void setTimestamp(Long timestamp) {
        super.setTimestamp(timestamp);
    }

    public String getSenderID() {
        return super.getSenderID();
    }

    public void setSenderID(String sender) {
        super.setSenderID(sender);
    }

    @Override
    public boolean getType() {
        return false;
    }
    public String getSenderName() {
        return super.getSenderName();
    }

    public void setSenderName(String senderName) {
        super.setSenderName(senderName);
    }
}
