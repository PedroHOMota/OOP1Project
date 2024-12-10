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

import static com.tus.gui.GuiUtil.verifyField;

import com.tus.exceptions.ItemAlreadyExists;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.GamePlatforms;
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

    //Constructor called when creating a new item
    public CreateUpdateItemView(final JFrame previousFrame, final ItemTypeEnum itemType, final User userLogged) {
        setContentPane(mainPanel);
        setVisible(true);

        switch (itemType){
            case GAME: {
                extraInfoLabel.setVisible(false);
                extraInfoTextField.setVisible(false);
                gamePlatformComboBox.addItem(GamePlatforms.PS3);
                gamePlatformComboBox.addItem(GamePlatforms.WII);
                gamePlatformComboBox.addItem(GamePlatforms.XBOX360);
                break;
            }
            case CD: {
                extraInfoTextField.setText("Artist:");
                gamePlatformComboBox.setVisible(false);
                break;
            }
            case BOOK:{
                extraInfoTextField.setText("Author:");
                gamePlatformComboBox.setVisible(false);
                break;
            }

        }

        createButton.addActionListener(e -> {
            try {
                final int units = Integer.parseInt(totalUnitsTextField.getText());
                InventoryMgmtRole temp = (InventoryMgmtRole) userLogged;
                Item item;

                verifyField(totalUnitsTextField,dateTextField,nameTextField);
                if (gamePlatformComboBox.isVisible()) {
                    item = new Game(nameTextField.getText(),dateTextField.getText(),units,units,(GamePlatforms) gamePlatformComboBox.getSelectedItem(),itemType);
                } else {
                    verifyField(extraInfoTextField);
                    if(itemType == ItemTypeEnum.BOOK){
                        item = new Book(nameTextField.getText(),dateTextField.getText(),units,units,extraInfoTextField.getText(),itemType);

                    }
                    else {
                        item = new Cd(nameTextField.getText(),dateTextField.getText(),units,units,extraInfoTextField.getText(),itemType);
                    }
                }

                temp.addItem(item);
                setVisible(false);
                previousFrame.setVisible(true);
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"Number of units has be an integer");
                ex.printStackTrace();
            }catch (ItemAlreadyExists ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"This item already exists");
                ex.printStackTrace();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"No field can be empty");
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(GuiUtil.backButtonAction(userLogged, previousFrame));
    }

    //Constructor called when updating item
    public CreateUpdateItemView(final JFrame previousFrame, final Item item, final User userLogged) {
        setContentPane(mainPanel);
        setVisible(true);

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
                InventoryMgmtRole temp = (InventoryMgmtRole) userLogged;

                item.setCreationDate(dateTextField.getText());
                item.setName(nameTextField.getText());
                item.setTotalUnits(Integer.parseInt(totalUnitsTextField.getText()));

                if(item.getItemType() == ItemTypeEnum.BOOK) {
                    verifyField(extraInfoTextField);
                    ((Book) item).setAuthor(extraInfoTextField.getText());
                } else {
                    verifyField(extraInfoTextField);
                    ((Cd) item).setArtist(extraInfoTextField.getText());
                }

                temp.updateItem(item);
                setVisible(false);
                previousFrame.setVisible(true);
            }catch (NumberFormatException ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"Number of units has be an integer");
                ex.printStackTrace();
            }catch (ItemAlreadyExists ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"This item already exists");
                ex.printStackTrace();
            }catch (Exception ex){
                JOptionPane.showMessageDialog(CreateUpdateItemView.this,"No field can be empty");
                ex.printStackTrace();
            }
        });

        backButton.addActionListener(GuiUtil.backButtonAction(userLogged, previousFrame));
    }
}
