package com.example.Entity;

import netscape.javascript.JSObject;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by User on 24/04/2017.
 */

@Entity
@Table(name = "signals")
public class Signal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private boolean isOpen;
    private int time;
    private String currency;
    private boolean isBuy;
    private double price;
    private double sellStop;
    private double sl;
    private double tp1;
    private double tp2;
    private String note;
    private String nameOfSl;
    @Temporal(TemporalType.TIMESTAMP)
    private Date ts = new Date();

    public Signal(){}

    public Signal(boolean isOpen, int time, String currency, boolean isBuy, double price, double sellStop, double sl, double tp1, double tp2, String note, String nameOfSl) {
        this.isOpen = isOpen;
        this.time = time;
        this.currency = currency;
        this.isBuy = isBuy;
        this.price = price;
        this.sellStop = sellStop;
        this.sl = sl;
        this.tp1 = tp1;
        this.tp2 = tp2;
        this.note = note;
        this.nameOfSl = nameOfSl;
    }

    public Signal(String currency, double price, double sellStop, double sl, double tp1, double tp2, String note) {
        this.currency = currency;
        this.price = price;
        this.sellStop = sellStop;
        this.sl = sl;
        this.tp1 = tp1;
        this.tp2 = tp2;
        this.note = note;
    }

    public Date getTs() {
        return ts;
    }

    public String getNameOfSl() {
        return nameOfSl;
    }

    public void setNameOfSl(String nameOfSl) {
        this.nameOfSl = nameOfSl;
    }

    @Override
    public String toString() {
        JSONObject object = new JSONObject();
        try {
            object.put("isOpen",isOpen);
            object.put("time",time);
            object.put("currency",currency);
            object.put("price",price);
            object.put("sellStop",sellStop);
            object.put("tp1",tp1);
            object.put("sl",sl);
            object.put("tp2",tp2);
            object.put("nameOfSl",nameOfSl);
            object.put("note",note);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return object.toString();
    }

    public int getId() {
        return id;
    }

    public boolean isOpen() {
        return isOpen;
    }

    public void setOpen(boolean open) {
        isOpen = open;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public boolean isBuy() {
        return isBuy;
    }

    public void setBuy(boolean buy) {
        isBuy = buy;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public double getSellStop() {
        return sellStop;
    }

    public void setSellStop(double sellStop) {
        this.sellStop = sellStop;
    }

    public double getSl() {
        return sl;
    }

    public void setSl(double sl) {
        this.sl = sl;
    }

    public double getTp1() {
        return tp1;
    }

    public void setTp1(double tp1) {
        this.tp1 = tp1;
    }

    public double getTp2() {
        return tp2;
    }

    public void setTp2(double tp2) {
        this.tp2 = tp2;
    }

    public String getNot() {
        return note;
    }

    public void setNot(String not) {
        this.note = not;
    }
}
