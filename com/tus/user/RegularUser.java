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

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.ItemNotFound;
import com.tus.exceptions.MaxiumAllowanceReached;
import com.tus.exceptions.NoAvailableUnits;
import com.tus.exceptions.UserAlreadyBorrowedACopyOfItem;
import com.tus.items.Item;
import com.tus.items.ItemTypeEnum;

public class RegularUser extends User implements RegularUserRole{
    private HashMap<Item,LocalDateTime> borrowedItems = new HashMap<Item, LocalDateTime>();

    public RegularUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType);
    }


    public void borrowItem(final String itemName, final ItemTypeEnum itemType)
        throws NoAvailableUnits, ItemNotFound, MaxiumAllowanceReached, UserAlreadyBorrowedACopyOfItem, FailedToSave {
        final LocalDateTime returnDate = LocalDateTime.now().plusDays(7);

        final Item item = regularUserDao.getItem(itemName, itemType);

        if(borrowedItems.containsKey(item)) throw new UserAlreadyBorrowedACopyOfItem(itemName);
        if(borrowedItems.size() == 10) throw new MaxiumAllowanceReached("User already has borrowed 10 items");
        if(item.borrowUnit() ){
            regularUserDao.updateItem(item);
            borrowedItems.put(item,returnDate);
        }
        else {
            throw new NoAvailableUnits("Item "+itemName+" doesnt have any available unit");
        }
    }

    public List checkOverdue(){
        final LocalDateTime now = LocalDateTime.now();
        final List<Map.Entry<Item, LocalDateTime>> entries = borrowedItems.entrySet().stream().filter(entry -> {
            return entry.getValue().compareTo(now) > 0;
        }).toList();

        return entries;
    }

    public void returnItem(Item item) throws ItemNotFound, FailedToSave {
        item.returnUnit();
        regularUserDao.updateItem(item);
        borrowedItems.remove(item);
    }

    public HashMap<Item, LocalDateTime> getBorrowedItems() {
        return borrowedItems;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Username: "+getUsername());
        builder.append("Borrowed items: {");
        for (var item: borrowedItems.entrySet()) {
            builder.append("name: "+item.getKey());
            builder.append("due date: "+item.getValue());
        }
        builder.append("}");

        return builder.toString();
    }
}
