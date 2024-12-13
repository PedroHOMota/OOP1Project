package com.tus.exceptions;

public class UserAlreadyExists extends Exception{
    public UserAlreadyExists(String userName){
        super("User "+userName+" already exists");
    }
}
