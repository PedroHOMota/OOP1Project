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
import javax.swing.table.TableColumn;

import static com.tus.gui.GuiUtil.backButtonAction;
import static com.tus.gui.GuiUtil.clearTable;
import static com.tus.gui.GuiUtil.populateTable;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.ItemDoesntExist;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.Item;
import com.tus.user.AdminUser;
import com.tus.user.EmployeeRole;
import com.tus.user.EmployeeUser;
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
    private JButton increaseQuantityButton;
    private JTextField increaseQuantityTextField;
    private DAO dao = DAOFactory.getDaoInstance();
    private DefaultTableModel model = (DefaultTableModel) infoTable.getModel();

    public ListViewItems(){
        setVisible(true);
        setSize(500, 200);
        setContentPane(mainPanel);

        String[][] data = {
            { "Kundan Kumar Jha", "4031", "CSE" },
            { "Anand Jha", "6014", "IT" }
        };

        //infoTable = new JTable(data,columnNames);
        TableColumn column = new TableColumn(0);
        TableColumn column2 = new TableColumn(1);
        TableColumn column3 = new TableColumn(2);
        column3.setHeaderValue("Test3");
        column2.setHeaderValue("Test2");
        column.setHeaderValue("Test1");


        infoTable.setShowGrid(true);
        var model = (DefaultTableModel) infoTable.getModel();
        model.addColumn("column");
        model.addColumn("column2");
        model.addColumn("column3");

        model.addRow(data[0]);
        model.addRow(data[1]);

        backButton.addActionListener(actionEvent -> {

        });

        increaseQuantityButton.addActionListener(actionEvent -> {
            try {
                JOptionPane.showMessageDialog(ListViewItems.this,infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString());
                final String itemName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                //RegularUser temp = (RegularUser) user;
                //temp.borrowItem(itemName);
            } catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"Please select an item to borrow");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"No available units to borrow");
            }
        });

    }

    public void UpdateTableAndFilters(Set set, boolean isItem, User user){

    }
    public ListViewItems(Set set, User user){
        infoTable.setShowGrid(true);
        setVisible(true);
        setSize(500, 200);
        setContentPane(mainPanel);


        PopulateTableAndFilterBoxForItems(user,set);
        filterButton.addActionListener(FilterForItems(user,set));

        backButton.addActionListener(backButtonAction(user,this));

        borrowItemButton.addActionListener(actionEvent -> {
            try {
                JOptionPane.showMessageDialog(ListViewItems.this,infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString());
                final String itemName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                RegularUser temp = (RegularUser) user;
                temp.borrowItem(itemName);
            } catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"Please select an item to borrow");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"No available units to borrow");
            }
        });

        increaseQuantityButton.addActionListener(actionEvent -> {
            try {
                final String quantity = increaseQuantityTextField.getText();
                final String itemName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                InventoryMgmtRole temp = (InventoryMgmtRole) user;
                temp.changeTotalNumberOfItemUnits(itemName,Integer.parseInt(quantity));
            } catch (ArrayIndexOutOfBoundsException ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"Please select an item to borrow");
            } catch (Exception ex){
                JOptionPane.showMessageDialog(ListViewItems.this,"No available units to borrow");
            }
        });

        deleteItemButton.addActionListener(e -> {
            final String itemName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
            InventoryMgmtRole temp = (InventoryMgmtRole) user;
            try {
                System.out.println(itemName);
                temp.removeItem(itemName);
                clearTable(model);
                populateTable(true,((RegularUserRole) temp).getAllItems(),model);
            } catch (ItemDoesntExist ex) {
                JOptionPane.showMessageDialog(ListViewItems.this,"Item doesnt exist; nothing to delete.");
                ex.printStackTrace();
            }
        });
    }

    private void PopulateTableAndFilterBoxForItems(User user, Set data) {
        model.addColumn("Name");
        model.addColumn("Published Date");
        model.addColumn("Game Platform/Artist/Author");
        model.addColumn("Available Units");
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
