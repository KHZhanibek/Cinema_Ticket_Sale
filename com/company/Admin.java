package com.company;

import java.sql.Time;

public class Admin implements People {
    protected String firstname, lastname, email, password;
    protected  int id, age;
    public Admin(String firstname, String lastname, String email, String password, int id, int age){
        this.firstname = firstname;
        this.lastname = lastname;
        this.email = email;
        this.password = password;
        this.id = id;
        this.age =age;
    }
    @Override
    public  int getId(){
        return id;
    }
    @Override
    public  int getAge(){
        return age;
    }
    @Override
    public  String getFirstName(){
        return firstname;
    }
    @Override
    public String getLastName(){
        return lastname;
    }
    @Override
    public  String getEmail(){
        return email;
    }
    @Override
    public  String getPassword(){
        return password;
    }
    @Override
    public void setAge(int age){
        this.age = age;
    }
    @Override
    public void setFirstName(String name){
        this.firstname = firstname;
    }
    @Override
    public void setLastName(String lastname){
        this.lastname = lastname;
    }
    @Override
    public void setEmail(String email){
        this.email = email;
    }
    @Override
    public void setPassword(String password){
        this.password = password;
    }


}
