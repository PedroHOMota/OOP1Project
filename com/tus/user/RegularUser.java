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

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.DataFormatException;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.items.Item;

public class RegularUser extends User{
    final private DAO dao = DAOFactory.getDaoInstance();

    private HashMap<Item,Date> borrowedItems = new HashMap<Item, Date>();

    public RegularUser(final String username, final String name, final String password, final UserTypesEnum userType) {
        super(username, name, password, userType);
    }


    private void borrowItem(Item item, Date returnDate){
        borrowedItems.put(item,returnDate);
    }

    private void checkOverdue(){

    }

    private Set<Item> listAll(){
        return dao.getAllItems();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append("Username: "+getUsername());
        for (var a: borrowedItems) {

        }
        builder.append(borrowedItems.)
        builder.indexOf("Username: {}")
        return "RegularUser{" + "borrowedItems=" + borrowedItems + '}';
    }
}
