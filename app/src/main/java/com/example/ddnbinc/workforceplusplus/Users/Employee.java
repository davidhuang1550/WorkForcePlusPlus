package com.example.ddnbinc.workforceplusplus.Users;

/**
 * Created by david on 2017-01-23.
 */

public abstract class Employee {
    private String FcmToken;
    private String StartDate;
    private String Email;
    private String EmployeeId;
    private String Password;
    private String Privilleges;

    public Employee(){

    }

    public Employee(String f, String s, String e,String p,String id,String priv){
        FcmToken=f;
        StartDate=s;
        Email=e;
        Password=p;
        EmployeeId=id;
        Privilleges=priv;
    }
    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getManagerId() {
        return FcmToken;
    }
    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void setManagerId(String managerId) {
        FcmToken = managerId;
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

}
