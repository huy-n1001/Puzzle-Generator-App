package com.example.myapplication;

public class Information {

    private String message;
    private int rating;

    public Information(){
    }

    public Information(String message, int rating){
        this.message = message;
        this.rating = rating;
    }

    public String getMessage(){
        return message;
    }
    public void setMessage(String message) {this.message = message;}

    public int getRating() {return rating;}

    public void setRating(int rating) {this.rating = rating;}
}
