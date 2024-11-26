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

import com.tus.user.AdminUser;
import com.tus.user.EmployeeUser;
import com.tus.user.RegularUser;
import com.tus.user.User;

public class MenuForUser {

    final private static Scanner SCANNER = new Scanner(System.in);

    public static void MenuForUser(AdminUser admin){

    }
    public static void MenuForUser(EmployeeUser employeeUser){

    }

    public static void MenuForUser(RegularUser regularUser){
        String op = "";
        Boolean keepRunning = true;


        while(true) {
            System.out.println("********MAIN MENU***********");
            System.out.println("1-Borrow item");
            System.out.println("2-Return item");
            System.out.println("3-List all borrowed items");
            System.out.println("4-List overdue items ");
            System.out.println("5-Logout ");
            System.out.println("Enter Option: ");
            op = SCANNER.nextLine();

            switch (op) {
                case "1": {

                }
                case "2": {
                }
                case "3": {
                }
                case "4": {
                }
                case "5": {
                    keepRunning = false;
                }
                default: {
                    System.out.println("Please, enter valid option");
                }
            }
        }
    }

    private Object subMenu(StringBuilder builder, Enum options){
//        for (var op: options
//             ) {
//
//        }
//        System.out.println(builder.toString());
//        final String op = SCANNER.nextLine();
//        return options.
        return null;
    }
}
