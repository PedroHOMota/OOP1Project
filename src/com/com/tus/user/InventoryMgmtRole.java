
package com.tus.user;

import java.util.HashSet;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemNotFound;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.Item;
import com.tus.items.ItemTypeEnum;

public interface InventoryMgmtRole {
    final DAO inventoryDao = DAOFactory.getDaoInstance();

    public default void addItem(Item item) throws ItemAlreadyExists {
        inventoryDao.saveItem(makeCopyOf(item));
    }

    public default void addItems(HashSet<Item> items) throws ItemAlreadyExists {
        inventoryDao.saveItems(makeCopyOf(items));
    }

    public default void removeItem(String itemName, ItemTypeEnum itemTypeEnum) throws ItemNotFound {
        Item item = inventoryDao.getItem(itemName, itemTypeEnum);
        inventoryDao.removeItem(item);
    }
    public default void updateItem(Item item) throws FailedToSave, ItemNotFound {
        inventoryDao.updateItem(item);
    }

    public default void changeTotalNumberOfItemUnits(String itemName, ItemTypeEnum itemTypeEnum, int amount) throws Exception{
        final Item item = inventoryDao.getItem(itemName, itemTypeEnum);
        item.changeAmountOfUnits(amount);
        inventoryDao.updateItem(item);
    }


        private Item makeCopyOf(Item item){
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

    private Item[] makeCopyOf(HashSet<Item> items){
        HashSet<Item> copySet = new HashSet();

        for(Item item : items){
            copySet.add(makeCopyOf(item));
        }

        return (Item[]) copySet.toArray();
    }
}
