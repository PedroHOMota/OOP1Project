package com.tus.gui;

import javax.swing.*;

import static com.tus.gui.GuiUtil.exitButtonAction;

import com.tus.user.User;

public class EmployeeAdminUserMenu extends JFrame{
    private JButton createNewUserButton;
    private JButton listAllItemsButton;
    private JPanel employeeAdminPanel;
    private JButton exitButton;
    private JButton createNewItemButton;
    private JButton listAllUsersButton;
    private JLabel welcomeLabel;

    public EmployeeAdminUserMenu(User user){

        welcomeLabel.setText("Welcome: "+user.getUsername());
        welcomeLabel.setSize(100,20);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        setContentPane(employeeAdminPanel);

        createNewUserButton.addActionListener(ActionEvent -> {
            setVisible(false);
            new CreateUpdateUserView(this,user);
        });

        exitButton.addActionListener(exitButtonAction(this));

        listAllItemsButton.addActionListener(ActionEvent -> {
            setVisible(false);
            new ListViewItems(user);
        });


        listAllUsersButton.addActionListener(ActionEvent -> {
            setVisible(false);
            new ListViewUsers(user);
        });

        createNewItemButton.addActionListener(e -> {
            setVisible(false);
            new CreateUpdateItemView(this, user);
        });
    }
}
