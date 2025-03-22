
package com.tus.user;

import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.items.ItemTypeEnum;

public final class AdminUser extends User implements AdminRole, EmployeeRole, InventoryMgmtRole, RegularUserRole{

    public AdminUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType, null);
    }

    @Override
    public void createAUser(final String username, final String name, final String password, final UserTypesEnum userType) throws UserAlreadyExists {
        if(userType == UserTypesEnum.ADMIN) {
            AdminUser adminUser = new AdminUser(username,name,password,userType);
            dao.saveUser(adminUser);
        }
        else if(userType == UserTypesEnum.EMPLOYEE){
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
    public void borrowItem(final String itemName, final ItemTypeEnum itemType) throws Exception {
        throw new Exception();
    }
}
