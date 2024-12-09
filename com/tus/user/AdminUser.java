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

import com.tus.exceptions.UserNotFound;

public class AdminUser extends User implements AdminRole, EmployeeRole, InventoryMgmtRole, RegularUserRole{

    public AdminUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType);
    }

    @Override
    public void createAUser(final String username, final String name, final String password, final UserTypesEnum userType) throws Exception{
        if(userType == UserTypesEnum.ADMIN) {
            AdminUser adminUser = new AdminUser(username,name,password,userType);
            dao.saveUser(adminUser);
        }
        if(userType == UserTypesEnum.EMPLOYEE){
            EmployeeUser employeeUser = new EmployeeUser(username,name,password,userType);
            dao.saveUser(employeeUser);
        } else {
            RegularUser regularUser = new RegularUser(username,name,password,userType);
            dao.saveUser(regularUser);
        }
    }

    public void deleteAUser(final String userName) throws UserNotFound {
        dao.removeUser(userName);
    }

    public void deleteAUser(final User user) throws UserNotFound {
        dao.removeUser(user);
    }

    @Override
    public void borrowItem(final String itemName) throws Exception {
        throw new Exception();
    }
}
