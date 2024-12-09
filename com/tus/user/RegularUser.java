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
import com.tus.items.Item;

public class RegularUser extends User implements RegularUserRole{
    private HashMap<Item,Date> borrowedItems = new HashMap<Item, Date>();

    public RegularUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType);
    }


    public void borrowItem(String itemName) throws Exception{
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final Date returnDate = dateFormat.parse(dateFormat.format(LocalDateTime.now().plusDays(7)));
        final Item item = regularUserDao.getItem(itemName);

        if(item.getAvailableUnits() > 0){
            regularUserDao.updateItem(item);
            borrowedItems.put(item,returnDate);
        }
        else {
            throw new Exception(); //Update ex
        }
    }

    public List checkOverdue() throws Exception{
        final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        final Date today = dateFormat.parse(dateFormat.format(LocalDateTime.now()));
        final List<Map.Entry<Item, Date>> entries = borrowedItems.entrySet().stream().filter(entry -> {
            return entry.getValue().compareTo(today) > 0;
        }).toList();

        return entries;
    }

    public void returnItem(Item item){
        borrowedItems.remove(item);
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
