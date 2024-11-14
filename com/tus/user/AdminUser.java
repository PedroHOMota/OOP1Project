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

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;

public class AdminUser extends User implements AdminRole, EmployeeRole{

    public AdminUser(final int id, final String username, final String name, final String password, final UserTypesEnum userType) {
        super(id, username, name, password, userType);
    }

    public AdminUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType);
    }

    public void deleteAUser(final User user) {
        //Remove user from db
    }

    public void createAUser(final User user) {
        //Add user to the db
    }
}
