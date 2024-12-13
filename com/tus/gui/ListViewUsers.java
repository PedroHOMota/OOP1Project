package com.tus.gui;

import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.Set;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import static com.tus.gui.GuiUtil.backButtonAction;
import static com.tus.gui.GuiUtil.clearTable;
import static com.tus.gui.GuiUtil.populateTable;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.exceptions.UserNotFound;
import com.tus.user.AdminUser;
import com.tus.user.EmployeeRole;
import com.tus.user.EmployeeUser;
import com.tus.user.RegularUser;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class ListViewUsers extends JFrame{
    private JPanel mainPanel;
    private JButton borrowItemButton;
    private JButton deleteUserButton;
    private JButton backButton;
    private JTable infoTable;
    private JComboBox filterComboBox;
    private JButton filterButton;
    private JButton updateUserButton;
    private JButton listBorrowedItemsButton;
    private DAO dao = DAOFactory.getDaoInstance();
    private DefaultTableModel model = (DefaultTableModel) infoTable.getModel();

    public ListViewUsers(User user){
        final Set<User> allUsersSet = ((EmployeeRole) user).getAllUsers();
        infoTable.setShowGrid(true);
        setVisible(true);
        setSize(500, 200);
        setContentPane(mainPanel);

        PopulateTableAndFilterBoxForUsers(allUsersSet, user.getUserType());
        filterButton.addActionListener(FilterForUsers(user,allUsersSet));
        backButton.addActionListener(backButtonAction(user,this));

        if(user.getUserType() != UserTypesEnum.ADMIN) {
            deleteUserButton.setEnabled(false);
            deleteUserButton.setVisible(false);
        }

        deleteUserButton.addActionListener(e -> {
            AdminUser temp = (AdminUser) user;
            try {
                final String userName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                final User userToDelete = temp.getUser(userName);
                if (userToDelete == user) {
                    JOptionPane.showMessageDialog(ListViewUsers.this, "Can not delete own user");
                } else {
                    temp.deleteAUser(userName);
                    clearTable(model);
                    populateTable(false, temp.getAllUsers(), model);

                }
            } catch (ArrayIndexOutOfBoundsException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewUsers.this,"Please select an item before proceeding");
            } catch(UserNotFound ex) {
                JOptionPane.showMessageDialog(ListViewUsers.this, "User doesnt exist; nothing to delete.");
                ex.printStackTrace();
            } catch (Exception ex){
                ex.printStackTrace();
            }
        });

        updateUserButton.addActionListener(e -> {
            setVisible(false);
            EmployeeRole temp = (EmployeeRole) user;
            try {
                final String userName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                final User userToUpdate = temp.getUser(userName);
                new CreateUpdateUserView(ListViewUsers.this, user, userToUpdate);
            } catch (ArrayIndexOutOfBoundsException ex){
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewUsers.this,"Please select an item before proceeding");
            } catch (UserNotFound ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewUsers.this, "User doesnt exist; nothing to update.");
            }
        });

        listBorrowedItemsButton.addActionListener(e -> {
            EmployeeRole temp = (EmployeeRole) user;
            try {
                final String userName = infoTable.getValueAt(infoTable.getSelectedRow(), 0).toString();
                final User userToList = temp.getUser(userName);
                if (userToList.getUserType() != UserTypesEnum.REGULAR) {
                    JOptionPane.showMessageDialog(ListViewUsers.this, "User cant borrow anything");
                }else {
                    setVisible(false);
                    new ViewBorrowedItems(ListViewUsers.this, userToList);
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewUsers.this, "Please select an item before proceeding");
            } catch (UserNotFound ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(ListViewUsers.this, "User doesnt exist; nothing to update.");
            }

        });
    }


    private void PopulateTableAndFilterBoxForUsers(Set data, UserTypesEnum currentUserType) {

        if(currentUserType == UserTypesEnum.ADMIN)
            filterComboBox.addItem(UserTypesEnum.ADMIN);

        filterComboBox.addItem(UserTypesEnum.REGULAR);
        filterComboBox.addItem(UserTypesEnum.EMPLOYEE);
        filterComboBox.addItem("All");

        HashSet<User> mySet = (HashSet<User>) data;
        model.addColumn("Username");
        model.addColumn("Name");
        model.addColumn("User Type");

        populateTable(false,mySet,model,currentUserType);
    }


    private ActionListener FilterForUsers(User user, Set data){
        return actionEvent -> {
            clearTable(model);

            final UserTypesEnum selection = (UserTypesEnum) filterComboBox.getSelectedItem();//.toString();
            Set<User> allUsersOfType = null;


            switch (selection){
                case ADMIN:{
                    allUsersOfType = dao.getAllUsersOfType(AdminUser.class);
                    break;
                }
                case REGULAR:{
                    allUsersOfType = dao.getAllUsersOfType(RegularUser.class);
                    break;
                }
                case EMPLOYEE: {
                    allUsersOfType = dao.getAllUsersOfType(EmployeeUser.class);
                    break;
                }
                default:{
                    allUsersOfType = dao.getAllUsers();
                    break;
                }
            }

            populateTable(false,allUsersOfType, model, user.getUserType());
        };
    }
}
