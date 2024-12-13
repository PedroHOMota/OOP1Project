
package com.tus.user;

import java.util.Set;

import com.tus.dataaccess.DAOFactory;
import com.tus.dataaccess.DAOMethods;
import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.NotSupportedException;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;

public interface EmployeeRole {

    DAOMethods dao = DAOFactory.getDaoInstance();

    //TODO update exception
    public default void createAUser(final String username, final String name, final String password, final UserTypesEnum userType) throws
        UserAlreadyExists, NotSupportedException {
        if(userType == UserTypesEnum.ADMIN) {
            throw new NotSupportedException();
        }
        if(userType == UserTypesEnum.EMPLOYEE){
            EmployeeUser employeeUser = new EmployeeUser(username,name,password,userType);
            dao.saveUser(employeeUser);
        } else {
            RegularUser employeeUser = new RegularUser(username,name,password,userType);
            dao.saveUser(employeeUser);
        }
    }

    public default void updateUserInfo(User userToUpdate) throws FailedToSave,UserNotFound{
        dao.updateUser(userToUpdate);
    }

    public default Set<User> getAllUsersOfType(final Class userClass){
        return dao.getAllUsersOfType(userClass);
    }

    public default Set<User> getAllUsers(){
        return dao.getAllUsers();
    }

    public default User getUser(String username) throws UserNotFound {
        return dao.getUser(username);
    }

}
