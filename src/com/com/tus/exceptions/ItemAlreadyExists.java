package com.tus.exceptions;

import java.util.List;

public class ItemAlreadyExists extends Exception{
    public ItemAlreadyExists(String name){
        super("Item "+name+" already exists");
    }

    public ItemAlreadyExists(List<String> items){
        super("Item "+items+" already exists");
    }
}
