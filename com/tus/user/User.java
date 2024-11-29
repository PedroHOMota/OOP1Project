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

package com.tus.user;

public class User {
    private String username;

    public String getName() {
        return name;
    }

    public UserTypesEnum getUserType() {
        return userType;
    }

    private String name;
    private String password;
    private UserTypesEnum userType;

    public User(final String username, final String name, final String password, final UserTypesEnum userType) {
        this.username = username;
        this.name = name;
        this.password = password;
        this.userType = userType;
    }

    public void resetPassword(String newPassword){
        password = newPassword;
    }

    public void resetUsername(String newUsername){
        username = newUsername;
    }

    public String getUsername(){
        return username;
    }

    public boolean validatePassword(String password){
        return password.equals(this.password);
    }

    public String toString(){
        return "Username: "+username+"\nUserType: "+userType.toString();
    }
}
