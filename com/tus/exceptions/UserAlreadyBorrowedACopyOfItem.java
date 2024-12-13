package com.tus.exceptions;

public class UserAlreadyBorrowedACopyOfItem extends Exception{
    public UserAlreadyBorrowedACopyOfItem(String itemName){
        super("Item "+itemName+" has already being borrowed by user");
    }
}
