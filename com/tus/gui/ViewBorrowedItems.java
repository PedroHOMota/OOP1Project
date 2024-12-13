package com.tus.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static com.tus.gui.GuiUtil.backButtonAction;
import static com.tus.gui.GuiUtil.clearTable;

import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.ItemNotFound;
import com.tus.items.Item;
import com.tus.user.RegularUser;
import com.tus.user.User;

public class ViewBorrowedItems extends JFrame {
    private JPanel mainPanel;
    private JTable itemTable;
    private JButton returnItemButton;
    private JButton backButton;
    private DefaultTableModel model = (DefaultTableModel) itemTable.getModel();


    public ViewBorrowedItems(JFrame previousFrame, User loggedUser){
        setContentPane(mainPanel);
        setVisible(true);
        setSize(500, 200);
        model.addColumn("Name");
        model.addColumn("Due Date");
        model.addColumn("Type");
        model.addColumn("Overdue");


        RegularUser regularUser = (RegularUser) loggedUser;
        clearTable(model);

        populateTable(regularUser.getBorrowedItems().entrySet(),regularUser.checkOverdue());

        returnItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    final String itemName = itemTable.getValueAt(itemTable.getSelectedRow(), 0).toString();
                    final Optional<Item> itemToReturn = regularUser.getBorrowedItems().keySet().stream().filter(item -> item.getName().equals(itemName)).findFirst();
                    if (!itemToReturn.isEmpty()) {
                        regularUser.returnItem(itemToReturn.get());
                        clearTable(model);
                        populateTable(regularUser.getBorrowedItems().entrySet(),regularUser.checkOverdue());
                    }
                } catch (ItemNotFound ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"Failed to return item");
                    ex.printStackTrace();
                } catch (FailedToSave ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"Failed to return item");
                    ex.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"Please select an item first");
                    ex.printStackTrace();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"General Error");
                    ex.printStackTrace();
                }
            }
        });


        backButton.addActionListener(backButtonAction(loggedUser,this));
    }

    public ViewBorrowedItems(JFrame previousFrame, User loggedUser, User userToList){
        setContentPane(mainPanel);
        setVisible(true);
        setSize(500, 200);
        model.addColumn("Name");
        model.addColumn("Due Date");
        model.addColumn("Type");
        model.addColumn("Overdue");


        RegularUser regularUser = (RegularUser) userToList;
        clearTable(model);

        populateTable(regularUser.getBorrowedItems().entrySet(),regularUser.checkOverdue());

        returnItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                try {
                    final String itemName = itemTable.getValueAt(itemTable.getSelectedRow(), 0).toString();
                    final Optional<Item> itemToReturn = regularUser.getBorrowedItems().keySet().stream().filter(item -> item.getName().equals(itemName)).findFirst();
                    if (!itemToReturn.isEmpty()) {
                        regularUser.returnItem(itemToReturn.get());
                        clearTable(model);
                        populateTable(regularUser.getBorrowedItems().entrySet(),regularUser.checkOverdue());
                    }
                } catch (ItemNotFound ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"Failed to return item");
                    ex.printStackTrace();
                } catch (FailedToSave ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"Failed to return item");
                    ex.printStackTrace();
                } catch (ArrayIndexOutOfBoundsException ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"Please select an item first");
                    ex.printStackTrace();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"General Error");
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(e -> GuiUtil.switchActiveFrame(ViewBorrowedItems.this,previousFrame));
    }

    private void populateTable(Set<Map.Entry<Item, LocalDateTime>> itemsSet, List overdueItems){


        for (Map.Entry<Item, LocalDateTime> entry: itemsSet) {
            //Name,Due Date,Type,Overdue
            model.addRow(new Object[] {entry.getKey().getName(),entry.getValue().toLocalDate().toString(),entry.getKey().getItemType(),overdueItems.contains(entry.getKey())});

        }
    }
}
