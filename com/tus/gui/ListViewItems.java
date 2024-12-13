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

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static com.tus.gui.GuiUtil.backButtonAction;
import static com.tus.gui.GuiUtil.clearTable;
import static com.tus.gui.GuiUtil.populateTable;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.ItemNotFound;
import com.tus.exceptions.MaxiumAllowanceReached;
import com.tus.exceptions.NoAvailableUnits;
import com.tus.exceptions.UserAlreadyBorrowedACopyOfItem;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.Item;
import com.tus.items.ItemTypeEnum;
import com.tus.user.InventoryMgmtRole;
import com.tus.user.RegularUser;
import com.tus.user.RegularUserRole;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class ListViewItems extends JFrame{
    private JPanel mainPanel;
    private JButton borrowItemButton;
    private JButton deleteItemButton;
    private JButton backButton;
    private JTable infoTable;
    private JComboBox filterComboBox;
    private JButton filterButton;
    private JButton updateItemButton;
    private DAO dao = DAOFactory.getDaoInstance();
    private DefaultTableModel model = (DefaultTableModel) infoTable.getModel();

    public ListViewItems(User userLogged){
        final Set<Item> allItemsSet = ((RegularUserRole) userLogged).getAllItems();
        infoTable.setShowGrid(true);
        setVisible(true);
        setSize(600, 200);
        setContentPane(mainPanel);

        if(userLogged.getUserType() == UserTypesEnum.REGULAR){
            deleteItemButton.setVisible(false);
            deleteItemButton.setEnabled(false);
            updateItemButton.setEnabled(false);
            updateItemButton.setVisible(false);
        } else {
            borrowItemButton.setVisible(false);
            borrowItemButton.setEnabled(false);
        }

        PopulateTableAndFilterBoxForItems(userLogged,allItemsSet);
        filterButton.addActionListener(FilterForItems(userLogged,allItemsSet));

        backButton.addActionListener(backButtonAction(userLogged,this));

        borrowItemButton.addActionListener(actionEvent -> {
            try {
                final String itemName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                final ItemTypeEnum itemType = (ItemTypeEnum) infoTable.getValueAt(infoTable.getSelectedRow(), 4);

                RegularUser regularUser = (RegularUser) userLogged;
                regularUser.borrowItem(itemName, itemType);
                clearTable(model);
                populateTable(true,regularUser.getAllItems(),model);
            } catch (ArrayIndexOutOfBoundsException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewItems.this,"Please select an item before proceeding");
            } catch (NoAvailableUnits ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewItems.this,"No available units to borrow");
            } catch (ItemNotFound ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewItems.this,"Could not find item");
            } catch (MaxiumAllowanceReached ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewItems.this,"Maximum allowance of 10 reached");
            } catch (UserAlreadyBorrowedACopyOfItem ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewItems.this,"You have already borrowed a copy of this item");
            } catch (Exception ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewItems.this,"General Error");
            }

        });

        deleteItemButton.addActionListener(e -> {
            InventoryMgmtRole temp = (InventoryMgmtRole) userLogged;
            try {
                final String itemName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                final ItemTypeEnum itemType = (ItemTypeEnum) infoTable.getValueAt(infoTable.getSelectedRow(), 4);

                temp.removeItem(itemName,itemType);
                clearTable(model);
                populateTable(true,((RegularUserRole) temp).getAllItems(),model);
            } catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"Please select an item before proceeding");
            } catch (ItemNotFound ex) {
                JOptionPane.showMessageDialog(ListViewItems.this,"Item doesnt exist; nothing to delete");
                ex.printStackTrace();
            }
        });

        updateItemButton.addActionListener(e -> {
            setVisible(false);
            Item itemToUpdate = null;
            try {
                final String itemName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                final ItemTypeEnum itemType = (ItemTypeEnum) infoTable.getValueAt(infoTable.getSelectedRow(), 4);

                final Item item = ((RegularUserRole) userLogged).getItem(itemName,itemType);
                new CreateUpdateItemView(ListViewItems.this,item,userLogged);

            } catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"Please select an item before proceeding");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"Failed to retrieve item.");
            }
        });
    }

    private void PopulateTableAndFilterBoxForItems(User user, Set data) {
        model.addColumn("Name");
        model.addColumn("Published Date");
        model.addColumn("Game Platform/Artist/Author");
        model.addColumn("Available Units");
        model.addColumn("Item Type");

        if(user.getUserType() == UserTypesEnum.ADMIN || user.getUserType() == UserTypesEnum.EMPLOYEE){
            model.addColumn("Total Units");
        }

        filterComboBox.addItem("Book");
        filterComboBox.addItem("Cd");
        filterComboBox.addItem("Game");
        filterComboBox.addItem("All");

        HashSet<Item> itemHashSet = (HashSet<Item>) data;
        populateTable(true,itemHashSet,model);
    }

    private ActionListener FilterForItems(User user, Set data){
        return new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent e) {
                clearTable(model);

                final String selection = filterComboBox.getSelectedItem().toString();
                Set<Item> allItemsOfTypeSet = null;

                switch (selection){
                    case "Game":{
                        allItemsOfTypeSet = dao.getAllItemsOfType(Game.class);
                        break;
                    }
                    case "Cd":{
                        allItemsOfTypeSet = dao.getAllItemsOfType(Cd.class);
                        break;
                    }
                    case "Book": {
                        allItemsOfTypeSet = dao.getAllItemsOfType(Book.class);
                        break;
                    }
                    default:{
                        allItemsOfTypeSet = dao.getAllItems();
                        break;
                    }
                }
                populateTable(true,allItemsOfTypeSet, model);
            }
        };
    }
}
