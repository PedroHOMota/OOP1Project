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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.tus.exceptions.ItemDoesntExist;
import com.tus.exceptions.NoAvailableUnits;
import com.tus.items.Item;

public class RegularUser extends User implements RegularUserRole{
    private HashMap<Item,LocalDateTime> borrowedItems = new HashMap<Item, LocalDateTime>();

    public RegularUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType);
    }


    public void borrowItem(String itemName) throws Exception{
        final LocalDateTime returnDate = LocalDateTime.now().plusDays(7);

        final Item item = regularUserDao.getItem(itemName);

        if(item.getAvailableUnits() > 0){
            regularUserDao.updateItem(item);
            borrowedItems.put(item,returnDate);
        }
        else {
            throw new NoAvailableUnits();
        }
    }

    public List checkOverdue() throws Exception{
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final Date today = dateFormat.parse(dateFormat.format(LocalDateTime.now()));
        final LocalDateTime now = LocalDateTime.now();
        final List<Map.Entry<Item, LocalDateTime>> entries = borrowedItems.entrySet().stream().filter(entry -> {
            return entry.getValue().compareTo(now) > 0;
        }).toList();

        return entries;
    }

    public void returnItem(Item item) throws ItemDoesntExist {
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
