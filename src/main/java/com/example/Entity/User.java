package com.example.Entity;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by User on 24/04/2017.
 */

@Entity
public class User {

    public enum Admin{
        SIGNAL_ADMIN,SUPER_ADMIN,USER;
    }

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;

    @Column(name = "email",unique = true,nullable = false)
    private String email;

    private long phoneNumber;

    private String name,lastName,password,country, countryCode,langSpeak,tradeLvl;
    private boolean isPaid;

    private Admin isAdmin;

    @Column(nullable = false)
    private String token;

    @Temporal(TemporalType.TIMESTAMP)
    private Date ts = new Date();

    public User(){}

//    public User(String name, String lastName, long phoneNumber, String email, String password, String country, String countryCode, String langSpeak, String tradeLvl, boolean isPaid ) {
//        this.name = name;
//        this.lastName = lastName;
//        this.phoneNumber = phoneNumber;
//        this.email = email;
//        this.password = password;
//        this.country = country;
//        this.countryCode = countryCode;
//        this.langSpeak = langSpeak;
//        this.tradeLvl = tradeLvl;
//        this.isPaid = isPaid;
//        //this.token = token;
//        this.isAdmin = Admin.USER;
//    }

    public User(String email, long phoneNumber, String name, String lastName, String password, String country, String countryCode, String langSpeak, String tradeLvl, String token) {
        this.email = email;
        this.phoneNumber = phoneNumber;
        this.name = name;
        this.lastName = lastName;
        this.password = password;
        this.country = country;
        this.countryCode = countryCode;
        this.langSpeak = langSpeak;
        this.tradeLvl = tradeLvl;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public Date getTs() {
        return ts;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public long getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(long phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getLangSpeak() {
        return langSpeak;
    }

    public void setLangSpeak(String langSpeak) {
        this.langSpeak = langSpeak;
    }

    public String getTradeLvl() {
        return tradeLvl;
    }

    public void setTradeLvl(String tradeLvl) {
        this.tradeLvl = tradeLvl;
    }

    public boolean isPaid() {
        return isPaid;
    }

    public void setPaid(boolean paid) {
        isPaid = paid;
    }

    public Admin getAdmin() {
        return isAdmin;
    }

    public void setAdmin(Admin admin) {
        isAdmin = admin;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
