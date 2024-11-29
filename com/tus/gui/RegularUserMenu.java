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

import com.tus.user.User;

public class RegularUserMenu extends JFrame{
    private JPanel regularUserMenuPanel;
    private JButton borrowBtn;
    private JButton borrowItemButton;
    private JButton listItemsButton;
    private JButton checkMyBorrowedItemsButton;

    public RegularUserMenu(){
        setTitle("Regular");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        setContentPane(regularUserMenuPanel);
        borrowBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                LoginMenu menu = new LoginMenu();
            }
        });
    }

    public RegularUserMenu(User user){
        setTitle("Regular");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        setContentPane(regularUserMenuPanel);
        borrowBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                new LoginMenu();
            }
        });
    }

    public JPanel getRegularUserMenuPanel() {
        return regularUserMenuPanel;
    }
}
