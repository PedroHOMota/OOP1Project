/*------------------------------------------------------------------------------
 *******************************************************************************
 * COPYRIGHT Ericsson 2024
 *
 * The copyright to the computer program(s) herein is the property of
 * Ericsson Inc. The programs may be used and/or copied only with written
 * permission from Ericsson Inc. or in accordance with the terms and
 * conditions stipulated in the agreement/contract under which the
 * program(s) have been supplied.
 *******************************************************************************
 *----------------------------------------------------------------------------*/

package com.tus;

import java.util.Scanner;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.UserNotFound;
import com.tus.user.User;

public class Menu {
    Scanner scanner = new Scanner(System.in);
    DAO dao = DAOFactory.getDaoInstance();
    public void login(){
        System.out.println("Enter username:");
        String username = scanner.next();
        System.out.println("Enter username:");
        String password = scanner.next();

        try{
            final User user = dao.getUser(username);
            if(user.validatePassword(username)){
                clearTerminal();
            } else {
                //throw new Exception(); //Invalid Password
            }

        }catch (UserNotFound userNotFound){
            System.out.println("user doesnt exist");
        }

    }

    public void displayMenu(){

    }

    public void clearTerminal(){
        System.out.print("\033\143");
    }
}
