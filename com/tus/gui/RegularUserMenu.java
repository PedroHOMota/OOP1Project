package com.tus.gui;

import javax.swing.*;

import static com.tus.gui.GuiUtil.exitButtonAction;

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
