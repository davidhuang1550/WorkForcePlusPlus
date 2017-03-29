package com.example.ddnbinc.workforceplusplus.Classes.Users;

import android.net.Uri;
import android.os.Parcelable;

import com.example.ddnbinc.workforceplusplus.Classes.Notifications.Notification;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by david on 2017-01-23.
 */

public abstract class Employee implements Serializable,Parcelable{

    private String FcmToken;
    private String StartDate;
    private String Email;
    private String EmployeeId;
    private String Password;
    private String Privilleges;
    private String Name;
    private ArrayList<Notification> notification;
    private HashMap<String,String> storedImages;

    public Employee(){

    }
    /*
    * @param f = FCM TOKEN
    * @param s = Start Date
    * @param e = Email
    * @param p = Password
    * @param id = Employee Id
    * @param priv = Privilleges
    * @param not = Notifications
    * @param n = Name
    * @param stored = Stored Images
    */

    public Employee(String f, String s, String e,String p,String id,String priv,ArrayList<Notification> not,String n,HashMap<String,String> stored){
        FcmToken=f;
        StartDate=s;
        Email=e;
        Password=p;
        EmployeeId=id;
        Privilleges=priv;
        notification=not;
        Name=n;
        storedImages=stored;
    }

    public ArrayList<Notification> getNotification() {
        return notification;
    }

    public void setNotification(ArrayList<Notification> notification) {
        this.notification = notification;
    }
    public void addNotification(Notification notifi){
        if(notification==null)notification = new ArrayList<>();
        notification.add(notifi);
    }
    public HashMap<String, String> getStoredImages() {
        return storedImages;
    }

    public void setStoredImages(HashMap<String, String> storedImages) {
        this.storedImages = storedImages;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


    public String getStartDate() {
        return StartDate;
    }

    public void setStartDate(String startDate) {
        StartDate = startDate;
    }
    public String getEmployeeId() {
        return EmployeeId;
    }

    public void setEmployeeId(String employeeId) {
        EmployeeId = employeeId;
    }

    public String getFcmToken() {
        return FcmToken;
    }

    public void setFcmToken(String fcmToken) {
        FcmToken = fcmToken;
    }
    public String getPrivilleges() {
        return Privilleges;
    }

    public void setPrivilleges(String privilleges) {
        Privilleges = privilleges;
    }
    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public void pushImage(String key, String path){
        if(storedImages == null) storedImages = new HashMap<>();
        storedImages.put(key,path);
    }

    public Uri getFindImagePath(String key){
        if(storedImages == null) storedImages = new HashMap<>();
        String temp =storedImages.get(key);
        return (temp!=null) ? Uri.parse(temp) : null ;
    }
}
