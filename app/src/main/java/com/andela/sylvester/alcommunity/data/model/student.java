package com.andela.sylvester.alcommunity.data.model;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Sylvester on 03/05/2017.
 */
public class student implements Serializable {
    private String CoderName;
    private String Name;
    private String Email;
    private String Phone;
    private String State;
    private String Region;
    private String Address;
    private String Password;
    private String PicUrl;

    public String getPicUrl() {
        return PicUrl;
    }

    public void setPicUrl(String picUrl) {
        PicUrl = picUrl;
    }

    public String getCoderName() {
        return CoderName;
    }

    public void setCoderName(String coderName) {
        CoderName = coderName;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getRegion() {
        return Region;
    }

    public void setRegion(String region) {
        Region = region;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    @Override
    public String toString() {
        return "student{" +
                "CoderName='" + CoderName + '\'' +
                ", Name='" + Name + '\'' +
                ", Email='" + Email + '\'' +
                ", Phone='" + Phone + '\'' +
                ", State='" + State + '\'' +
                ", Region='" + Region + '\'' +
                ", Address='" + Address + '\'' +
                ", Password='" + Password + '\'' +
                '}';
    }
}
