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
import java.util.stream.Collectors;

import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemDoesntExist;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.items.Item;
import com.tus.user.User;

public class DAO implements DAOMethods{

    private HashSet<User> users = new HashSet<User>();
    private HashSet<Item> items = new HashSet<Item>();

    public User getUser(final String username) throws UserNotFound {
        final User aUser = users.stream().filter(user -> user.getUsername().equals(username)).findFirst().orElseThrow(() -> new UserNotFound() );

        return aUser;
    }

    public void saveUser(final User user) throws UserAlreadyExists {
        if(!users.add(user)){
            throw new UserAlreadyExists();
        }
    }

    public void updateUser(final User user) throws UserNotFound {
        if(users.remove(user)){
            users.add(user);
        }

        throw new UserNotFound();
    }

    public void removeUser(final String username) throws UserNotFound {
        users.remove(getUser(username));
    }

    public Item getItem(final String id) throws ItemDoesntExist {
        return null;
    }

    public boolean saveItem(final Item item) throws ItemAlreadyExists {
        if(!this.items.add(item))
            throw new ItemAlreadyExists();
        
        return false;
    }

    public boolean saveItems(final Collection<Item> items) throws ItemAlreadyExists {
        this.items.addAll(items);
        items.forEach(item -> {
            this.items.contains(item);
        });

        final List<Item> itemsAlreadySaved = items.stream().filter(item -> this.items.contains(item)).collect(Collectors.toList());

        if(itemsAlreadySaved.size() > 0) {
            throw new ItemAlreadyExists();
        }

        return false;
    }

    public boolean updateItem(final Item item) throws ItemDoesntExist {
        return false;
    }

    public boolean removeItem(final Item item) throws ItemDoesntExist {
        return false;
    }
}
