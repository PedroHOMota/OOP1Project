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

import com.tus.dataaccess.DAOFactory;
import com.tus.dataaccess.DAOMethods;

public interface EmployeeRole {

    DAOMethods dao = DAOFactory.getDaoInstance();

    public default void createAUser(String username, String name, String password, UserTypesEnum userType) throws Exception{
        if(userType == UserTypesEnum.ADMIN) {
            return;
        }
        if(userType == UserTypesEnum.EMPLOYEE){
            EmployeeUser employeeUser = new EmployeeUser(username,name,password,userType);
            dao.saveUser(employeeUser);
        } else {
            RegularUser employeeUser = new RegularUser(username,name,password,userType);
            dao.saveUser(employeeUser);
        }
    }

    public default void updateAUserPassword(String username, String newPassword) throws Exception{
        final User user = dao.getUser(username);

        user.resetPassword(newPassword);

        dao.updateUser(user);
    }
}
