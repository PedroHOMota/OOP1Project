package com.tus.gui;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.*;

import static com.tus.gui.GuiUtil.backButtonAction;
import static com.tus.gui.GuiUtil.switchActiveFrame;
import static com.tus.gui.GuiUtil.verifyField;

import com.tus.exceptions.ItemAlreadyExists;
import com.tus.exceptions.ItemNotFound;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.GamePlatformsEnum;
import com.tus.items.Item;
import com.tus.items.ItemTypeEnum;
import com.tus.user.InventoryMgmtRole;
import com.tus.user.User;

public class CreateUpdateItemView extends JFrame{
    private JButton backButton;
    private JButton createButton;
    private JTextField nameTextField;
    private JTextField totalUnitsTextField;
    private JTextField dateTextField;
    private JTextField extraInfoTextField;
    private JLabel extraInfoLabel;
    private JComboBox gamePlatformComboBox;
    private JPanel mainPanel;
    private JComboBox itemTypeComboBox;

    //Constructor called when creating a new item
    public CreateUpdateItemView(final JFrame previousFrame, final User userLogged) {
        setContentPane(mainPanel);
        setVisible(true);
        setSize(400,300);

        ItemTypeEnum itemType = ItemTypeEnum.GAME;
        extraInfoLabel.setVisible(false);
        extraInfoTextField.setVisible(false);
        gamePlatformComboBox.addItem(GamePlatformsEnum.PS3);
        gamePlatformComboBox.addItem(GamePlatformsEnum.WII);
        gamePlatformComboBox.addItem(GamePlatformsEnum.XBOX360);

        itemTypeComboBox.addItem(ItemTypeEnum.GAME);
        itemTypeComboBox.addItem(ItemTypeEnum.CD);
        itemTypeComboBox.addItem(ItemTypeEnum.BOOK);

        itemTypeComboBox.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(final ItemEvent e) {
                if(e.getStateChange() != ItemEvent.SELECTED) return;

                switch ((ItemTypeEnum) itemTypeComboBox.getSelectedItem()) {
                    case GAME: {
                        extraInfoLabel.setVisible(false);
                        extraInfoTextField.setVisible(false);
                        gamePlatformComboBox.setVisible(true);
                        gamePlatformComboBox.setEnabled(true);
                        gamePlatformComboBox.removeAllItems();
                        gamePlatformComboBox.addItem(GamePlatformsEnum.PS3);
                        gamePlatformComboBox.addItem(GamePlatformsEnum.WII);
                        gamePlatformComboBox.addItem(GamePlatformsEnum.XBOX360);
                        break;
                    }
                    case CD: {
                        extraInfoLabel.setText("Artist:");
                        extraInfoLabel.setVisible(true);
                        extraInfoTextField.setVisible(true);
                        gamePlatformComboBox.setVisible(false);
                        gamePlatformComboBox.setEnabled(false);
                        break;
                    }
                    case BOOK: {
                        extraInfoLabel.setText("Author:");
                        extraInfoLabel.setVisible(true);
                        extraInfoTextField.setVisible(true);
                        gamePlatformComboBox.setVisible(false);
                        gamePlatformComboBox.setEnabled(false);
                        break;
                    }
                }
            }
        });

        createButton.addActionListener(e -> {
            try {
                verifyField(totalUnitsTextField,dateTextField,nameTextField);
                InventoryMgmtRole inventoryMgmtUser = (InventoryMgmtRole) userLogged;

                Item item = null;

                if(itemTypeComboBox.getSelectedItem() == ItemTypeEnum.BOOK) {
                    item = new Book(
                        nameTextField.getText(),
                        dateTextField.getText(),
                        Integer.parseInt(totalUnitsTextField.getText()),
                        Integer.parseInt(totalUnitsTextField.getText()),
                        extraInfoTextField.getText(),
                        ItemTypeEnum.BOOK
                    );
                    verifyField(extraInfoTextField);
                } else if(itemTypeComboBox.getSelectedItem() == ItemTypeEnum.CD){
                    verifyField(extraInfoTextField);
                    item = new Cd(
                        nameTextField.getText(),
                        dateTextField.getText(),
                        Integer.parseInt(totalUnitsTextField.getText()),
                        Integer.parseInt(totalUnitsTextField.getText()),
                        extraInfoTextField.getText(),
                        ItemTypeEnum.CD
                    );
                } else {
                    item = new Game(
                        nameTextField.getText(),
                        dateTextField.getText(),
                        Integer.parseInt(totalUnitsTextField.getText()),
                        Integer.parseInt(totalUnitsTextField.getText()),
                        (GamePlatformsEnum) gamePlatformComboBox.getSelectedItem(),
                        ItemTypeEnum.GAME
                    );
                }

                inventoryMgmtUser.addItem(item);
                setVisible(false);
                setEnabled(false);

                new EmployeeAdminUserMenu(userLogged);
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"Number of units must be an integer");
                ex.printStackTrace();
            }catch (ItemAlreadyExists ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"This item already exists");
                ex.printStackTrace();
            }catch (ItemNotFound ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"Could not find item");
                ex.printStackTrace();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"No field can be empty");
                ex.printStackTrace();
            }
        });


        backButton.addActionListener(backButtonAction(userLogged,this));

    }

    //Constructor called when updating item
    public CreateUpdateItemView(final JFrame previousFrame, final Item item, final User userLogged) {
        setContentPane(mainPanel);
        setVisible(true);
        setSize(400,300);
        itemTypeComboBox.setVisible(false);
        itemTypeComboBox.setEnabled(false);

        createButton.setText("Update");
        nameTextField.setText(item.getName());
        dateTextField.setText(item.getCreationDate().toString());
        totalUnitsTextField.setText(""+item.getTotalUnits());
        switch (item.getItemType()){
            case GAME: {
                extraInfoLabel.setVisible(false);
                extraInfoTextField.setVisible(false);
                gamePlatformComboBox.addItem(item.getItemType());
                gamePlatformComboBox.setEnabled(false);
                break;
            }
            case CD: {
                extraInfoLabel.setText("Artist:");
                extraInfoTextField.setText(((Cd)item).getArtist());
                gamePlatformComboBox.setVisible(false);
                break;
            }
            case BOOK:{
                extraInfoLabel.setText("Author:");
                extraInfoTextField.setText(((Book)item).getAuthor());
                gamePlatformComboBox.setVisible(false);
                break;
            }

        }

        createButton.addActionListener(e -> {
            try {
                verifyField(totalUnitsTextField,dateTextField,nameTextField);
                InventoryMgmtRole inventoryMgmtUser = (InventoryMgmtRole) userLogged;


                item.setCreationDate(dateTextField.getText());
                item.setName(nameTextField.getText());
                item.changeAmountOfUnits(Integer.parseInt(totalUnitsTextField.getText()));

                if(item.getItemType() == ItemTypeEnum.BOOK) {
                    verifyField(extraInfoTextField);
                    ((Book) item).setAuthor(extraInfoTextField.getText());
                } else if(item.getItemType() == ItemTypeEnum.CD){
                    verifyField(extraInfoTextField);
                    ((Cd) item).setArtist(extraInfoTextField.getText());
                }

                inventoryMgmtUser.updateItem(item);

                setVisible(false);
                setEnabled(false);

                previousFrame.setVisible(true);
                new ListViewItems(userLogged);
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"Number of units has be an integer");
                ex.printStackTrace();
            }catch (ItemAlreadyExists ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"This item already exists");
                ex.printStackTrace();
            }catch (ItemNotFound ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"Could not find item");
                ex.printStackTrace();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"No field can be empty");
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(e -> {
            switchActiveFrame(CreateUpdateItemView.this,previousFrame);
        });
    }
}
