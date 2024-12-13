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

import com.tus.exceptions.NotSupportedException;
import com.tus.items.ItemTypeEnum;

public class EmployeeUser extends User implements EmployeeRole, InventoryMgmtRole,RegularUserRole{

    public EmployeeUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType);
    }

    @Override
    public void borrowItem(final String itemName, final ItemTypeEnum itemType) throws Exception {
        throw new Exception();
    }
}
