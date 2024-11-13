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

package com.tus.dataaccess;

import java.util.Collection;
import java.util.Collections;

import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemDoesntExist;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.items.Item;
import com.tus.user.User;

public interface DAOMethods {

    public Object getUser(String username) throws UserNotFound;
    public Object saveUser(User user) throws UserAlreadyExists;
    public Object updateUser(User user) throws UserNotFound;
    public Object removeUser(String username) throws UserNotFound;

    public boolean getItem(String id) throws ItemDoesntExist;
    public boolean saveItem(Item item) throws ItemAlreadyExists;
    public boolean saveItem(Collection<Item> items) throws ItemAlreadyExists;
    public boolean updateItem(Item item) throws ItemDoesntExist;
    public boolean removeItem(Item item) throws ItemDoesntExist;
}