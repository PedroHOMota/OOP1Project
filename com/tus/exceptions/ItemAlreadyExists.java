package com.tus.exceptions;

public class ItemAlreadyExists extends Exception{
    public ItemAlreadyExists(String name){
        super("Item "+name+" already exists");
    }
}
