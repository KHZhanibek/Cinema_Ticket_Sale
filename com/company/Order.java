package com.company;

import java.util.*;

public class Order {
    private Customer cust;
    private Movie movik;
    private int num_kid_ticket, num_adult_ticket, id, discont;
    private static int counter = 0;
    private static Map<Customer, Integer>count_cust = new HashMap();
    public Order(Customer cust, Movie movik, int num_kid_ticket, int num_adult_ticket){
        this.cust = cust;
        this.movik = movik;
        this.num_adult_ticket = num_adult_ticket;
        this.num_kid_ticket = num_kid_ticket;
        counter++;
        id = counter;
        count_cust.replace(cust, count_cust.get(cust) + 1);
        int num_of_visits = count_cust.get(cust);
        if(num_of_visits <= 2)
            discont = 0;
        else if(num_of_visits <= 5)
            discont = 3;
        else if(num_of_visits <= 10)
            discont = 7;
        else
            discont = 20;
    }
    public int getCustId(){
        return cust.getId();
    }
    public String getMoviename(){
        return movik.getName();
    }
    public int getKids_ticket(){
        return num_kid_ticket;
    }
    public int getAdult_ticket(){
        return num_adult_ticket;
    }
    public int getId(){
        return id;
    }
    public int getDiscont(){
        return discont;
    }
    public int getTotalWage(){
        return (num_kid_ticket * 1000 + num_adult_ticket * 1500) * (100 - discont) / 100;
    }


}
