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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemNotFound;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.items.Item;
import com.tus.items.ItemTypeEnum;
import com.tus.user.User;

public class DAO implements DAOMethods{

    private HashSet<User> users = new HashSet<User>();
    private HashSet<Item> items = new HashSet<Item>();

    public User getUser(final String username) throws UserNotFound {
        final User aUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElseThrow(() -> new UserNotFound());

        return aUser;
    }

    public void saveUser(final User user) throws UserAlreadyExists {
        try {
            if(getUser(user.getUsername()) != null){
                throw new UserAlreadyExists(user.getUsername());
            }
        } catch (UserNotFound e) {
            final boolean add = users.add(user);
            if(!add){
                throw new UserAlreadyExists(user.getUsername());
            }
        }

    }

    public void updateUser(final User user) throws FailedToSave,UserNotFound {
        removeUser(user);
        if(!users.add(user)){
            throw new FailedToSave(user.getUsername());
        }
    }

    public void removeUser(final String username) throws UserNotFound {
        users.remove(getUser(username));
    }

    public void removeUser(final User user) throws UserNotFound {
        if(!users.remove(user)){
            throw new UserNotFound();
        }
    }

    public Set<User> getAllUsersOfType(final Class userClass){
        return users.stream().filter(user -> user.getClass().equals(userClass)).collect(Collectors.toSet());
    }

    public Set<User> getAllUsers(){
        return users;
    }

    public Item getItem(final String itemName, ItemTypeEnum itemType) throws ItemNotFound {
        return  items.stream().filter(item -> item.getName().equals(itemName) && item.getItemType().equals(itemType)).findFirst().orElseThrow(() -> new ItemNotFound(itemName) );
    }

    public Set<Item> getAllItemsOfType(final Class itemClass){
        return items.stream().filter(item -> item.getClass().equals(itemClass)).collect(Collectors.toSet());
    }

    public Set<Item> getAllItems(){
        return items;
    }

    public boolean saveItem(final Item item ) throws ItemAlreadyExists {
        try {
            if(getItem(item.getName(), item.getItemType()) != null){
                throw new ItemAlreadyExists(item.getName());
            }
        } catch ( ItemNotFound e) {
            if(!items.add(item)){
                throw new ItemAlreadyExists(item.getName());
            }
        }

        return false;
    }

    public boolean saveItems(final Collection<Item> items) throws ItemAlreadyExists {
        this.items.addAll(items);
        items.forEach(item -> {
            this.items.contains(item);
        });

        final List<Item> itemsAlreadySaved = items.stream().filter(item -> this.items.contains(item)).collect(Collectors.toList());

        if(itemsAlreadySaved.size() > 0) {
            throw new ItemAlreadyExists("");
        }

        return false;
    }

    public void updateItem(final Item item) throws FailedToSave, ItemNotFound {
        removeItem(item);
        if(!items.add(item)) {
            throw new FailedToSave(item.getName());
        }

    }

    public void removeItem(final Item item) throws ItemNotFound {
        if(!items.remove(item)){
            throw new ItemNotFound(item.getName());
        }
    }
}
