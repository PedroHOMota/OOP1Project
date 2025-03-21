package com.tus.exceptions;

public class FailedToSave extends Exception{
    public FailedToSave(String name){
        super("Failed to save "+name+" to db");
    }
}
