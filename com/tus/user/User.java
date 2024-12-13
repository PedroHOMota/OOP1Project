
package com.tus.user;

public abstract class User {
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

    public void setPassword(String newPassword){
        password = newPassword;
    }

    public void setUsername(String newUsername){
        username = newUsername;
    }

    public void setName(String newName){ name = newName; }

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
