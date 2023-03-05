package com.example.client.model;

import java.io.Serializable;
import java.sql.Date;

public class Result implements Serializable {
    int id;
    String result;
    int timestap;
    int analizWayId;

    Date date;

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getResult() {
        return result;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public int getTimestap() {
        return timestap;
    }

    public void setTimestap(int timestap) {
        this.timestap = timestap;
    }

    public int getAnalizWayId() {
        return analizWayId;
    }

    public void setAnalizWayId(int analizWayId) {
        this.analizWayId = analizWayId;
    }
}
