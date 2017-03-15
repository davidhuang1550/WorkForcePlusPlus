package com.example.ddnbinc.workforceplusplus.Classes.Message;

/**
 * Created by davidhuang on 2017-03-11.
 */

public class ImageMessage extends Message {
    private String ImageKey;

    public ImageMessage(){

    }
    public ImageMessage(String SenderID, Long Timestamp,String SenderName){
        super(SenderID,Timestamp,SenderName);
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
    public String getImageKey() {
        return ImageKey;
    }

    public void setImageKey(String imageKey) {
        ImageKey = imageKey;
    }

    @Override
    public boolean getType() {
        return true;
    }

    public String getSenderName() {
        return super.getSenderName();
    }

    public void setSenderName(String senderName) {
        super.setSenderName(senderName);
    }
}
