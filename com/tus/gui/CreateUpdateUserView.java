package com.tus.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import static com.tus.gui.GuiUtil.backButtonAction;
import static com.tus.gui.GuiUtil.switchActiveFrame;
import static com.tus.gui.GuiUtil.verifyField;

import com.tus.exceptions.FailedToSave;
import com.tus.exceptions.NotSupportedException;
import com.tus.exceptions.UserAlreadyExists;
import com.tus.exceptions.UserNotFound;
import com.tus.user.EmployeeRole;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class CreateUpdateUserView extends JFrame{
    private JButton backButton;
    private JButton createButton;
    private JTextField usernameField;
    private JTextField passwordField;
    private JTextField nameField;
    private JComboBox userTypeComboBox;
    private JPanel mainPane;

    //Constructor called when updating a user
    public CreateUpdateUserView(JFrame previousFrame, User userLogged, User userToUpdate) {
        setVisible(true);
        setSize(500, 200);
        setContentPane(mainPane);

        createButton.setText("Update");
        usernameField.setText(userToUpdate.getUsername());
        nameField.setText(userToUpdate.getName());

        userTypeComboBox.setEnabled(false);
        userTypeComboBox.addItem(userToUpdate.getUserType());
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                EmployeeRole temp = (EmployeeRole) userLogged;

                try {
                    verifyField(usernameField, nameField);

                    if (!passwordField.getText().isEmpty()) {
                        userToUpdate.setPassword(passwordField.getText());
                    }

                    userToUpdate.setUsername(usernameField.getText());
                    userToUpdate.setName(nameField.getText());
                    ((EmployeeRole) userLogged).updateUserInfo(userToUpdate);
                    setVisible(false);
                    new ListViewUsers(userLogged);
                } catch (FailedToSave ex){
                    JOptionPane.showMessageDialog(CreateUpdateUserView.this,"Failed to save user update");
                    ex.printStackTrace();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(CreateUpdateUserView.this,"Cant complete request");
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(e -> {
            setVisible(true);
            switchActiveFrame(CreateUpdateUserView.this,previousFrame);
        });
    }

    //Constructor called when creating a user
    public CreateUpdateUserView(JFrame previousFrame, User userLogged) {
        setVisible(true);
        setSize(500, 200);
        setContentPane(mainPane);

        createButton.setText("Create");

        if(userLogged.getUserType().equals(UserTypesEnum.ADMIN))
            userTypeComboBox.addItem(UserTypesEnum.ADMIN);

        userTypeComboBox.addItem(UserTypesEnum.EMPLOYEE);
        userTypeComboBox.addItem(UserTypesEnum.REGULAR);
        createButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                EmployeeRole temp = (EmployeeRole) userLogged;
                try {
                    verifyField(usernameField,nameField,passwordField);
                    temp.createAUser(usernameField.getText(), nameField.getText(), passwordField.getText(), (UserTypesEnum) userTypeComboBox.getSelectedItem());

                    switchActiveFrame(CreateUpdateUserView.this,previousFrame);

                } catch (UserAlreadyExists ex){
                    JOptionPane.showMessageDialog(CreateUpdateUserView.this,"User with this details already exists");
                    ex.printStackTrace();
                } catch (NotSupportedException ex){
                    JOptionPane.showMessageDialog(CreateUpdateUserView.this,"Current logged user cant perform this action");
                    ex.printStackTrace();
                } catch (Exception ex){
                    JOptionPane.showMessageDialog(CreateUpdateUserView.this,"Cant complete request");
                    ex.printStackTrace();
                }
            }
        });

        backButton.addActionListener(backButtonAction(userLogged,this));
    }
}
