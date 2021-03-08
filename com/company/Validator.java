package com.company;

public class Validator {
    public static boolean checkPassword(String x){
        if(x.length() < 8) return false;
        boolean lcase = false, ucase = false, nums = false, chars = false;
        for(int i = 0; i < x.length(); i++){
            char xi = x.charAt(i);
            if(xi == Character.toLowerCase(xi) && Character.isLetter(xi))
                lcase = true;
            else if(xi == Character.toUpperCase(xi) && Character.isLetter(xi))
                ucase = true;
            else if(Character.isDigit(xi))
                nums = true;
            else
                chars = true;
        }
        System.out.println(lcase +" "+ ucase +" "+ nums +" "+ chars);
        return lcase & ucase & nums & chars;
    }
    public static boolean checkEmail(String x){
        int sob = 0, dot = 0;
        for(int i = 0; i < x.length(); i++){
            char xi = x.charAt(i);
            if(xi == '@')
                sob = i;
            if(xi == '.')
                dot = i;
        }
        if(0 < sob && sob < dot && dot + 1 < x.length())
           return true;
        return false;
    }
}
