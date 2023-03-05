package com.example.client.model;


import java.io.Serializable;

public class Prognos implements Serializable, Cloneable {
    private int date;
    private double value;

    public Prognos() {
    }

    @Override
    public Prognos clone() throws CloneNotSupportedException {
        return (Prognos) super.clone();
    }

    public Prognos(int date, double value) {
        this.date = date;
        this.value = value;
    }

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return date + " " + value;
    }
}
