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

import java.util.HashSet;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.items.Book;
import com.tus.items.Game;
import com.tus.items.Item;

public interface InventoryMgmtRole {
    final DAO inventoryDao = DAOFactory.getDaoInstance();

    public default void addItem(Item item){
        inventoryDao.saveItem(makeCopyOf(item));
    }

    public default void addItems(HashSet<Item> items){
        inventoryDao.saveItems(makeCopyOf(items));
    }

    public default void removeItem(Item item){
        inventoryDao.removeItem(makeCopyOf(item));
    }
    public default void updateItem(Item item){
        inventoryDao.updateItem(makeCopyOf(item));
    }

    private Item makeCopyOf(Item item){
        if(item.getClass() == Book.class){
            Book copy = new Book(item.getName(),
                item.getPublishedDate(),
                item.getTotalUnits(),
                item.getAvailableUnits(),
                ((Book) item).getAuthor());

            return copy;
        }
//        else if (item.getClass() == Game.class) {
//            Game copy = new Game(
//                item.getName(),
//                item.getPublishedDate(),
//                item.getTotalUnits(),
//                item.getAvailableUnits(),
//                ((Game) item).getPlatform());
//
//            return copy;
//        }
        else {
            Game copy = new Game(item.getName(),
                item.getPublishedDate(),
                item.getTotalUnits(),
                item.getAvailableUnits(),
                ((Game) item).getPlatform());

            return copy;
        }
    }

    private HashSet<Item> makeCopyOf(HashSet<Item> items){
        HashSet copySet = new HashSet();

        for(Item item : items){
            copySet.add(makeCopyOf(item));
        }

        return copySet;
    }
}
