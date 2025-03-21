
package com.tus.user;

import java.util.Set;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.ItemNotFound;
import com.tus.items.Item;
import com.tus.items.ItemTypeEnum;

public interface RegularUserRole {
    final DAO regularUserDao = DAOFactory.getDaoInstance();

    public default Set<Item> getAllItems(){
        return regularUserDao.getAllItems();
    }

    public default Set<Item> getAllOfType(Class itemClass){
        return regularUserDao.getAllItemsOfType(itemClass);
    }

    public default Item getItem(final String name, final ItemTypeEnum itemType) throws ItemNotFound {
        return regularUserDao.getItem(name, itemType);
    }

    public void borrowItem(final String itemName, final ItemTypeEnum itemType) throws Exception;
}
