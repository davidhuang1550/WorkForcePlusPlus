package com.example.ddnbinc.workforceplusplus.Classes.Users;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.ddnbinc.workforceplusplus.Classes.Notifications.Notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david on 2017-01-23.
 * reason for duplication incase of future implementations of different components
 */

public class TeamMember extends Employee implements Serializable,Parcelable {

    public TeamMember(){

    }
    /**
    * @param f = FCM TOKEN
    * @param s = Start Date
    * @param e = Email
    * @param p = Password
    * @param id = Employee Id
    * @param priv = Privilleges
    * @param noti = Notifications
    * @param n = Name
    * @param stored = Stored Images
    */

    public TeamMember(String f, String s, String e, String p, String id, String priv,
                      ArrayList<Notification>noti,String n,HashMap<String,String> stored){
        super(f,s,e,p,id,priv,noti,n,stored);
    }
    public ArrayList<Notification> getNotification() {
        return super.getNotification();
    }

    public void setNotification(ArrayList<Notification> notification) {
        super.setNotification(notification);
    }
    public String getEmail() {
        return super.getEmail();
    }

    public void setEmail(String email) {
        super.setEmail(email);
    }

    public String getPassword() {
        return super.getPassword();
    }

    public void setPassword(String password) {
        super.setPassword(password);
    }

    public String getStartDate() {
        return super.getStartDate();
    }

    public void setStartDate(String startDate) {
        super.setStartDate(startDate);
    }
    public String getEmployeeId() {
        return super.getEmployeeId();
    }

    public void setEmployeeId(String employeeId) {
        super.setEmployeeId(employeeId);
    }

    public String getFcmToken() {
        return super.getFcmToken();
    }

    public void setFcmToken(String fcmToken) {
        super.setFcmToken(fcmToken);
    }
    public String getPrivileges() {
        return super.getPrivileges();
    }
    public String getName() {
        return super.getName();
    }

    public void setName(String name) {
        super.setName(name);
    }


    public HashMap<String, String> getStoredImages() {
        return super.getStoredImages();
    }

    public void setStoredImages(HashMap<String, String> storedImages) {
        super.setStoredImages(storedImages);
    }
    public void setPrivileges(String privilleges) {
        super.setPrivileges(privilleges);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        setFcmToken(dest.readString());
        setStartDate(dest.readString());
        setEmail(dest.readString());
        setEmployeeId(dest.readString());
        setPassword(dest.readString());
        setPrivileges(dest.readString());
        setName(dest.readString());

    }

    protected TeamMember(Parcel in) {
        setFcmToken(in.readString());
        setStartDate(in.readString());
        setEmail(in.readString());
        setEmployeeId(in.readString());
        setPassword(in.readString());
        setPrivileges(in.readString());
        setName(in.readString());

    }

    public static final Parcelable.Creator<TeamMember> CREATOR = new Parcelable.Creator<TeamMember>() {
        @Override
        public TeamMember createFromParcel(Parcel in) {
            return new TeamMember(in);
        }

        @Override
        public TeamMember[] newArray(int size) {
            return new TeamMember[size];
        }
    };
    public int describeContents(){
        return 0;
    }
}
