package com.example.myapplication;

public class reviewObject {

    public int rating = 0;
    public String message = "default message";

    public reviewObject() {}

    public void setRating(int val) {
        this.rating = val;
    }

    public void setMessage(String msg) {
        this.message = msg;
    }
}
