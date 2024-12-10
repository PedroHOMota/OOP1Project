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
import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static com.tus.gui.GuiUtil.backButtonAction;

import com.tus.exceptions.ItemDoesntExist;
import com.tus.items.Item;
import com.tus.user.RegularUser;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class ViewBorrowedItems extends JFrame {
    private JPanel mainPanel;
    private JTable itemTable;
    private JButton returnItemButton;
    private JButton backButton;
    private DefaultTableModel model = (DefaultTableModel) itemTable.getModel();


    public ViewBorrowedItems(JFrame previousFrame, User loggedUser){
        setContentPane(mainPanel);
        setVisible(true);
        model.addColumn("Name");
        model.addColumn("Due Date");
        model.addColumn("Type");
        model.addColumn("Overdue");

        backButton.addActionListener(e -> {
            backButtonAction(loggedUser,previousFrame);
        });

        RegularUser temp = (RegularUser) loggedUser;
        GuiUtil.clearTable(model);

        try {
            final List overdueItems = temp.checkOverdue();
        } catch (Exception ex) {}
        for (Map.Entry<Item, LocalDateTime> entry: temp.getBorrowedItems().entrySet()) {
            model.addRow(new Object[] {entry.getKey().getName(),entry.getValue().toLocalDate().toString(),entry.getKey().getItemType(),entry.getKey()});

        }
        returnItemButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final String itemName = itemTable.getValueAt(itemTable.getSelectedRow(), 0).toString();
                RegularUser temp = (RegularUser) loggedUser;
                final Optional<Item> itemm = temp.getBorrowedItems().keySet().stream().filter(item -> item.getName().equals(itemName)).findFirst();

                try {
                    if (!itemm.isEmpty()) {
                        final Item item1 = itemm.get();
                        temp.returnItem(item1);
                    }
                } catch (ItemDoesntExist ex){
                    JOptionPane.showMessageDialog(ViewBorrowedItems.this,"Failed to return item");
                    ex.printStackTrace();
                }
            }
        });
    }
}
