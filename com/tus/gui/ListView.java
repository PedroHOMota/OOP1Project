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

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.Item;
import com.tus.user.AdminUser;
import com.tus.user.EmployeeRole;
import com.tus.user.EmployeeUser;
import com.tus.user.RegularUser;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class ListView extends JFrame{
    private JPanel mainPanel;
    private JButton button1;
    private JButton deleteItemButton;
    private JButton backButton;
    private JTable infoTable;
    private JComboBox filterComboBox;
    private JButton filterButton;
    private JButton increaseQuantityButton;
    private DAO dao = DAOFactory.getDaoInstance();
    private DefaultTableModel model = (DefaultTableModel) infoTable.getModel();

    public ListView(){
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

        backButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

            }
        });

    }

    public ListView(Set set, boolean isItem, User user){
        infoTable.setShowGrid(true);
        setVisible(true);
        setSize(500, 200);
        setContentPane(mainPanel);

        if(isItem){
            PopulateTableAndFilterBoxForItems(user,set);
            filterButton.addActionListener(FilterForItems(user,set));
        } else {
            PopulateTableAndFilterBoxForUsers(set);
            filterButton.addActionListener(FilterForUsers(user,set));

        }
        backButton.addActionListener(backButtonAction(user));

        button1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    JOptionPane.showMessageDialog(ListView.this,infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString());
                } catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(ListView.this,"Please select an item");
                }

            }
        });



//        filterButton.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(final ActionEvent e) {
//                FilterForUsers(null,set);
//                final String selection = filterComboBox.getSelectedItem().toString();
//                JOptionPane.showMessageDialog(ListView.this,selection);
//            }
//        });
    }


    public void PopulateTableAndFilterBoxForUsers(Set data) {
        filterComboBox.addItem("Admin");
        filterComboBox.addItem("Regular");
        filterComboBox.addItem("Employee");
        filterComboBox.addItem("All");

        HashSet<User> mySet = (HashSet<User>) data;
        model.addColumn("Username");
        model.addColumn("Name");
        model.addColumn("User Type");
        for (User lUser: mySet) {
            model.addRow(new Object[] {lUser.getUsername(), lUser.getName(),lUser.getUserType()});
        }

    }

    public ActionListener FilterForUsers(User user, Set data){
        return new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent e) {
                int rowCount = model.getRowCount();
                for(int i=0; i < rowCount;i++){
                    System.out.println("index: "+i+" modelSize: "+model.getRowCount());
                    model.removeRow(0);
                }

                HashSet<User> mySet = (HashSet<User>) data;

                final String selection = filterComboBox.getSelectedItem().toString();
                Set<User> allUsersOfType = null;

                switch (selection){
                    case "Admin":{
                        allUsersOfType = dao.getAllUsersOfType(AdminUser.class);
                        break;
                    }
                    case "Regular":{
                        allUsersOfType = dao.getAllUsersOfType(RegularUser.class);
                        break;
                    }
                    case "Employee": {
                        allUsersOfType = dao.getAllUsersOfType(EmployeeUser.class);
                        break;
                    }
                    default:{
                        allUsersOfType = dao.getAllUsers();
                        break;
                    }

                }

                for (User lUser: allUsersOfType) {
                    model.addRow(new Object[] {lUser.getUsername(), lUser.getName(),lUser.getUserType()});
                    System.out.println("user: "+lUser.getUsername()+" size: "+allUsersOfType.size()+" modelSize: "+model.getRowCount());
                }
            }
        };
    }


    public void PopulateTableAndFilterBoxForItems(User user, Set data) {
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

        HashSet<Item> mySet = (HashSet<Item>) data;
        for (Item item : mySet) {
            if(item.getClass() == Game.class){
                Game game = (Game) item;
                model.addRow(new Object[] {game.getName(), game.getPublishedDate(),game.getPlatform(),
                    game.getAvailableUnits(),game.getTotalUnits()});
            } else if(item.getClass() == Book.class){
                Book book = (Book) item;
                model.addRow(new Object[] {book.getName(), book.getPublishedDate(),book.getAuthor(),
                    book.getAvailableUnits(),book.getTotalUnits()});
            } else {
                Cd cd = (Cd) item;
                model.addRow(new Object[] {cd.getName(), cd.getPublishedDate(),cd.getArtist(),
                    cd.getAvailableUnits(),cd.getTotalUnits()});
            }
        }
    }


    public ActionListener FilterForItems(User user, Set data){
        return new ActionListener(){
            @Override
            public void actionPerformed(final ActionEvent e) {
                int rowCount = model.getRowCount();
                for(int i=0; i < rowCount;i++){
                    System.out.println("index: "+i+" modelSize: "+model.getRowCount());
                    model.removeRow(0);
                }

                HashSet<Item> mySet = (HashSet<Item>) data;

                final String selection = filterComboBox.getSelectedItem().toString();
                Set<Item> allUsersOfType = null;

                switch (selection){
                    case "Game":{
                        allUsersOfType = dao.getAllItemsOfType(Game.class);
                        break;
                    }
                    case "Cd":{
                        allUsersOfType = dao.getAllItemsOfType(Cd.class);
                        break;
                    }
                    case "Book": {
                        allUsersOfType = dao.getAllItemsOfType(Book.class);
                        break;
                    }
                    default:{
                        allUsersOfType = dao.getAllItems();
                        break;
                    }

                }

                for (Item item : mySet) {
                    if(item.getClass() == Game.class){
                        Game game = (Game) item;
                        model.addRow(new Object[] {game.getName(), game.getPublishedDate(),game.getPlatform(),
                            game.getAvailableUnits(),game.getTotalUnits()});
                    } else if(item.getClass() == Book.class){
                        Book book = (Book) item;
                        model.addRow(new Object[] {book.getName(), book.getPublishedDate(),book.getAuthor(),
                            book.getAvailableUnits(),book.getTotalUnits()});
                    } else {
                        Cd cd = (Cd) item;
                        model.addRow(new Object[] {cd.getName(), cd.getPublishedDate(),cd.getArtist(),
                            cd.getAvailableUnits(),cd.getTotalUnits()});
                    }
                }
            }
        };
    }

    private ActionListener backButtonAction(User user){
        setVisible(false);
        if(UserTypesEnum.REGULAR == user.getUserType()){
            return new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    new RegularUserMenu(user);
                }

            };
        } else {
            return new ActionListener() {
                @Override
                public void actionPerformed(final ActionEvent e) {
                    new EmployeeAdminUserMenu((EmployeeRole) user);
                }

            };
        }
    }
}
