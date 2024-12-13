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
import java.util.Set;

import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemNotFound;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.items.Item;
import com.tus.items.ItemTypeEnum;
import com.tus.user.User;

public interface DAOMethods {

    public User getUser(String username) throws UserNotFound;
    public void saveUser(User user) throws UserAlreadyExists;
    public void updateUser(User user) throws FailedToSave,UserNotFound;
    public void removeUser(String username) throws UserNotFound;
    public void removeUser(User user) throws UserNotFound;
    public Set<User> getAllUsersOfType(Class userClass);
    public Set<User> getAllUsers();

    public Item getItem(String id, ItemTypeEnum itemType) throws ItemNotFound;
    public boolean saveItem(Item item) throws ItemAlreadyExists;
    public boolean saveItems(Collection<Item> items) throws ItemAlreadyExists;
    public void updateItem(Item item) throws FailedToSave, ItemNotFound;
    public void removeItem(Item item) throws ItemNotFound;
    public Set<Item> getAllItemsOfType(Class userClass);
    public Set<Item> getAllItems();
}
