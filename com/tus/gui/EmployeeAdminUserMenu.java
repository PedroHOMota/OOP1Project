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

import javax.swing.*;

import static com.tus.gui.GuiUtil.exitButtonAction;

import com.tus.user.EmployeeRole;
import com.tus.user.RegularUserRole;
import com.tus.user.User;

public class EmployeeAdminUserMenu extends JFrame{
    private JButton createNewUserButton;
    private JButton listAllItemsButton;
    private JPanel employeeAdminPanel;
    private JButton exitButton;
    private JButton createNewItemButton;
    private JButton listAllUsersButton;

    public EmployeeAdminUserMenu(EmployeeRole user){

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        setContentPane(employeeAdminPanel);

        createNewUserButton.addActionListener(ActionEvent -> {
            setVisible(false);
        });

        exitButton.addActionListener(exitButtonAction(this));

        listAllItemsButton.addActionListener(ActionEvent -> {
            setVisible(false);
            new ListViewItems(((RegularUserRole) user).getAllItems(), (User) user);
        });


        listAllUsersButton.addActionListener(ActionEvent -> {
            setVisible(false);
            new ListViewUsers(user.getAllUsers(),(User) user);
        });
    }
}
