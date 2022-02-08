package com.example.employeemanagementsystem.email;

import org.springframework.stereotype.Service;

import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/*
* email validator class is implementing an already existed interface
* this interface has a method called test, and it is checking if the email address
* which the user wants to create is valid or not
  */
@Service
public class EmailValidator implements Predicate<String> {

    @Override
    public boolean test(String email) {
        //this regex is providing the information on how the email should looks like
        String regex = "^[a-z]+[a-z0-9.]*@[a-z0-9.]+\\.[a-z]{2,4}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
}
