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

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.UserNotFound;
import com.tus.user.User;

public class LoginMenu extends JFrame {

    private final DAO dao = DAOFactory.getDaoInstance();
    private JPanel mainPanel;
    private JTextField usernameField;
    private JLabel passwordLbl;
    private JLabel usernameLbl;
    private JTextField passwordField;
    private JButton loginBtn;

    public LoginMenu(){
        User user = null;
        setTitle("User login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        setContentPane(mainPanel);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                User tuser = loginUser(usernameField.getText(),passwordField.getText());
                if(tuser == null){
                    JOptionPane.showMessageDialog(LoginMenu.this,"equas Username not found");
                } else {
                    JOptionPane.showMessageDialog(LoginMenu.this,"Username not found");
                }
            }
        });
    }

    private User loginUser(String username, String password){
        try{
            final User user = dao.getUser(username);
            if(user.validatePassword(username)){
                return user;
            } else {
                //Alert wrong password
            }

        }catch (UserNotFound userNotFound){
            //Alert user doest exist
            System.out.println("user doesnt exist");
        }
        finally {
            return null;
        }
    }
}
