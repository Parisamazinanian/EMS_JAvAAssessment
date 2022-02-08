package com.example.employeemanagementsystem.email;
/*
* This is an interface for sending email
* this email has a method send
*/
public interface EmailSender {
    void send(String to, String email);
}
