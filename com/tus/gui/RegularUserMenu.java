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

import static com.tus.gui.GuiUtil.exitButtonAction;

import com.tus.user.RegularUser;
import com.tus.user.User;

public class RegularUserMenu extends JFrame{
    private JPanel regularUserMenuPanel;
    private JButton listItemsButton;
    private JButton checkMyBorrowedItemsButton;
    private JButton exitButton;

    public RegularUserMenu(User user){
        setTitle("Regular");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        setContentPane(regularUserMenuPanel);

        final RegularUserMenu regularUserMenu = this;
        exitButton.addActionListener(exitButtonAction(this));

        checkMyBorrowedItemsButton.addActionListener(e -> {
            setVisible(false);
            new ViewBorrowedItems(this,user);
        });

        listItemsButton.addActionListener(e ->{
            setVisible(false);
            new ListViewItems(user);
        });
    }

}
