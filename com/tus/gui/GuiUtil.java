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

package com.tus.gui;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.Item;
import com.tus.user.EmployeeRole;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class GuiUtil {

    static void clearTable(final DefaultTableModel model){
        int rowCount = model.getRowCount();
        for(int i=0; i < rowCount;i++){
            model.removeRow(0);
        }
    }

    static void switchActiveFrame(final JFrame toHide, final JFrame toShow){
        toHide.setVisible(false);
        toHide.setEnabled(false);
        toHide.dispose();
        toShow.setVisible(true);
        toShow.setEnabled(true);
    }

    static void populateTable(final boolean isItem, Set mySet, final DefaultTableModel model){
        if(isItem) {
            HashSet<Item> tempSet = (HashSet<Item>) mySet;
            for (Item item : tempSet) {
                if(item.getClass() == Game.class){
                    Game game = (Game) item;
                    model.addRow(new Object[] {game.getName(), game.getCreationDate(),game.getPlatform(),
                        game.getAvailableUnits(),item.getItemType(), game.getTotalUnits()});
                } else if(item.getClass() == Book.class){
                    Book book = (Book) item;
                    model.addRow(new Object[] {book.getName(), book.getCreationDate(),book.getAuthor(),
                        book.getAvailableUnits(),item.getItemType(), book.getTotalUnits()});
                } else {
                    Cd cd = (Cd) item;
                    model.addRow(new Object[] {cd.getName(), cd.getCreationDate(),cd.getArtist(),
                        cd.getAvailableUnits(),item.getItemType(), cd.getTotalUnits()});
                }
            }
        } else {
            HashSet<User> tempSet = (HashSet<User>) mySet;

            for (User lUser : tempSet) {
                model.addRow(new Object[] { lUser.getUsername(), lUser.getName(), lUser.getUserType() });
            }
        }
    }

    static void populateTable(final boolean isItem, Set mySet, final DefaultTableModel model, UserTypesEnum currentUserType){
        if(isItem) {
            HashSet<Item> tempSet = (HashSet<Item>) mySet;
            for (Item item : tempSet) {
                if(item.getClass() == Game.class){
                    Game game = (Game) item;
                    model.addRow(new Object[] {game.getName(), game.getCreationDate(),game.getPlatform(),
                        game.getAvailableUnits(),game.getTotalUnits()});
                } else if(item.getClass() == Book.class){
                    Book book = (Book) item;
                    model.addRow(new Object[] {book.getName(), book.getCreationDate(),book.getAuthor(),
                        book.getAvailableUnits(),book.getTotalUnits()});
                } else {
                    Cd cd = (Cd) item;
                    model.addRow(new Object[] {cd.getName(), cd.getCreationDate(),cd.getArtist(),
                        cd.getAvailableUnits(),cd.getTotalUnits()});
                }
            }
        } else {
            HashSet<User> tempSet = (HashSet<User>) mySet;

            for (User lUser: tempSet) {
                if(currentUserType != UserTypesEnum.ADMIN && lUser.getUserType() == UserTypesEnum.ADMIN)
                    continue;
                else {
                    model.addRow(new Object[] {lUser.getUsername(), lUser.getName(),lUser.getUserType()});
                }
            }
        }
    }

    static ActionListener backButtonAction(User user, JFrame frame){

        if(UserTypesEnum.REGULAR == user.getUserType()){
            return ActionListener -> {
                frame.setVisible(false);
                frame.dispose();
                new RegularUserMenu(user);
            };
        } else {
            return ActionListener -> {
                frame.setVisible(false);
                frame.dispose();
                new EmployeeAdminUserMenu(user);
            };
        }
    }

    static ActionListener exitButtonAction(final JFrame frame){
        return ActionEvent -> {
            frame.setVisible(false);
            frame.setEnabled(false);
            frame.dispose();
            new LoginMenu();
        };
    }

    static void verifyField(JTextField... fields) throws Exception{
        for (JTextField field:fields) {
            if(field.getText().isEmpty()) throw new Exception();
        }
    }
}
