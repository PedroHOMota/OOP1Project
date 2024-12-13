package com.tus.exceptions;

public class ItemNotFound extends Exception{
    public ItemNotFound(String name){
        super("Item "+name+" not found in db");
    }
}
