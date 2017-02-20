package com.example.ddnbinc.workforceplusplus.Classes.Users;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by david on 2017-01-23.
 * reason for duplication incase of future implementations of different components
 */

public class TeamMember extends Employee implements Serializable,Parcelable {

    public TeamMember(){

    }
    public TeamMember(String f, String s, String e,String p,String id,String priv){
        super(f,s,e,p,id,priv);
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
    public String getPrivilleges() {
        return super.getPrivilleges();
    }

    public void setPrivilleges(String privilleges) {
        super.setPrivilleges(privilleges);
    }
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        setFcmToken(dest.readString());
        setStartDate(dest.readString());
        setEmail(dest.readString());
        setEmployeeId(dest.readString());
        setPassword(dest.readString());
        setPrivilleges(dest.readString());

    }

    protected TeamMember(Parcel in) {
        setFcmToken(in.readString());
        setStartDate(in.readString());
        setEmail(in.readString());
        setEmployeeId(in.readString());
        setPassword(in.readString());
        setPrivilleges(in.readString());
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
