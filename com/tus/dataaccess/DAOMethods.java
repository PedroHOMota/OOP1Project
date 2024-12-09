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
import java.util.Set;
import java.util.stream.Collectors;

import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemDoesntExist;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.items.Item;
import com.tus.user.User;

public interface DAOMethods {

    public User getUser(String username) throws UserNotFound;
    public void saveUser(User user) throws UserAlreadyExists;
    public void updateUser(User user) throws UserNotFound, UserAlreadyExists;
    public void removeUser(String username) throws UserNotFound;
    public void removeUser(User user) throws UserNotFound;
    public Set<User> getAllUsersOfType(Class userClass);
    public Set<User> getAllUsers();

    public Item getItem(String id) throws ItemDoesntExist;
    public boolean saveItem(Item item) throws ItemAlreadyExists;
    public boolean saveItems(Collection<Item> items) throws ItemAlreadyExists;
    public void updateItem(Item item) throws ItemDoesntExist;
    public void removeItem(Item item) throws ItemDoesntExist;
    public Set<Item> getAllItemsOfType(Class userClass);
    public Set<Item> getAllItems();
}
