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

import java.util.Set;

import com.tus.dataaccess.DAOFactory;
import com.tus.dataaccess.DAOMethods;
import com.tus.exceptions.UserNotFound;

public interface EmployeeRole {

    DAOMethods dao = DAOFactory.getDaoInstance();

    //TODO update exception
    public default void createAUser(final String username, final String name, final String password, final UserTypesEnum userType) throws Exception{
        if(userType == UserTypesEnum.ADMIN) {
            throw new Exception();
        }
        if(userType == UserTypesEnum.EMPLOYEE){
            EmployeeUser employeeUser = new EmployeeUser(username,name,password,userType);
            dao.saveUser(employeeUser);
        } else {
            RegularUser employeeUser = new RegularUser(username,name,password,userType);
            dao.saveUser(employeeUser);
        }
    }

    public default void updateAUserPassword(final String username, final String newPassword) throws Exception{
        final User user = dao.getUser(username);

        user.resetPassword(newPassword);

        dao.updateUser(user);
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
