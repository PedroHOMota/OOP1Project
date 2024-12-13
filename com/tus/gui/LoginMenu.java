package com.tus.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.UserNotFound;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class LoginMenu extends JFrame {

    private final DAO dao = DAOFactory.getDaoInstance();
    private JPanel mainPanel;
    private JTextField usernameField;
    private JLabel passwordLbl;
    private JLabel usernameLbl;
    private JTextField passwordField;
    private JButton loginBtn;
    private JPanel LoginPanel;

    public LoginMenu(){
        setTitle("User login");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400,300);
        setVisible(true);
        setContentPane(mainPanel);

        loginBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(final ActionEvent e) {
                final User tempUser = loginUser(usernameField.getText(),passwordField.getText());
                if(tempUser != null){
                    setVisible(false);
                    if(tempUser.getUserType() == UserTypesEnum.REGULAR)
                        new RegularUserMenu(tempUser);
                    else
                        new EmployeeAdminUserMenu(tempUser);
                }
            }
        });
    }

    private User loginUser(String username, String password){
        try{
            final User user = dao.getUser(username);
            if(user.validatePassword(password)){
                return user;
            } else {
                JOptionPane.showMessageDialog(LoginMenu.this,"Wrong password");
                return null;
            }

        }catch (UserNotFound userNotFound){
            JOptionPane.showMessageDialog(LoginMenu.this,"User not found");
            return null;
        }
    }
}
