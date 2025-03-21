
package com.tus.dataaccess;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemNotFound;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
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
        } catch (UserNotFound _) {
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

    public boolean saveItem(final Item itemToSave ) throws ItemAlreadyExists {
        final Item item = makeCopyOf(itemToSave);
        try {
            if(getItem(item.getName(), item.getItemType()) != null){
                throw new ItemAlreadyExists(item.getName());
            }
        } catch (ItemNotFound _) {
            if(!items.add(item)){
                throw new ItemAlreadyExists(item.getName());
            }
        }

        return false;
    }

    public void saveItems(final Item... items) throws ItemAlreadyExists {
        final List<Item> itemsToSave = Arrays.stream(items).filter(item -> !this.items.contains(item))
            .map(item -> makeCopyOf(item))
            .collect(Collectors.toList());
        for (var item: itemsToSave) {
            this.items.add(item);
        }
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


    private Item makeCopyOf(Item item){
        switch (item){
            case Book book : {break;}
            case Game game :{
                break;
            }
            case Cd cd:{
                break;
            }
        }
        if(item.getClass() == Book.class){
            Book copy = new Book(item.getName(),
                item.getCreationDate(),
                item.getTotalUnits(),
                item.getAvailableUnits(),
                ((Book) item).getAuthor(),
                item.getItemType());

            return copy;
        }
        else if (item.getClass() == Game.class) {
            Game copy = new Game(
                item.getName(),
                item.getCreationDate(),
                item.getTotalUnits(),
                item.getAvailableUnits(),
                ((Game) item).getPlatform(),
                item.getItemType());

            return copy;
        }
        else {
            Cd copy = new Cd(item.getName(),
                item.getCreationDate(),
                item.getTotalUnits(),
                item.getAvailableUnits(),
                ((Cd) item).getArtist(),
                item.getItemType());

            return copy;
        }
    }

    private Item[] makeCopyOf(Item[] items){
        HashSet<Item> copySet = new HashSet();

        for(Item item : items){
            copySet.add(makeCopyOf(item));
        }

        return (Item[]) copySet.toArray();
    }
}
