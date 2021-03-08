package com.company;
import java.sql.*;

public class Movie {
    private int id;
    private String name;
    public static int cnt = 0;
    public Movie(String name){
        this.name = name;
        this.id = ++cnt;
    }
    public Movie(int id, String name){
        this.name = name;
        this.id = id;
    }
    public  int getId(){
        return id;
    }
    public  String getName(){
        return name;
    }
    public void setName(String name){
        this.name = name;
    }



}
