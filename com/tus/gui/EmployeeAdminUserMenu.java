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
import javax.swing.*;

import com.tus.user.EmployeeRole;
import com.tus.user.RegularUserRole;
import com.tus.user.User;

public class EmployeeAdminUserMenu extends JFrame{
    private JButton createNewUserButton;
    private JButton deleteItemButton;
    private JButton deleteUserButton;
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
        createNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

            }
        });

        createNewUserButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {

            }
        });

        exitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setVisible(true);
                new LoginMenu();
            }
        });

        listAllItemsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setVisible(false);
                new ListViewItems(((RegularUserRole)user).getAllItems(),(User) user);
            }
        });

        listAllUsersButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                setVisible(false);
                new ListViewUsers(user.getAllUsers(),(User) user);
            }
        });
    }
}
