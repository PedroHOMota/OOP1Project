
package com.tus.user;

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
