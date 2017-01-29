package com.example.ddnbinc.workforceplusplus.Classes.Users;

import java.io.Serializable;

/**
 * Created by david on 2017-01-23.
 */

public class Manager extends Employee implements Serializable {

    public Manager(){

    }
    public Manager(String f, String s, String e,String p,String id,String priv){
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

}
