//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tus;

import javax.swing.text.html.ListView;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.gui.ListViewItems;
import com.tus.gui.ListViewUsers;
import com.tus.gui.LoginMenu;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.user.AdminUser;
import com.tus.user.EmployeeUser;
import com.tus.user.RegularUser;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class Main {

    private static void createSampleData(){
        DAO dao = DAOFactory.getDaoInstance();
        AdminUser admin = new AdminUser("admin","administrator","pass", UserTypesEnum.ADMIN);
        User employee = new EmployeeUser("employee","AnEmployee","pass",UserTypesEnum.EMPLOYEE);
        User regularUser = new RegularUser("regular","AnUser","pass",UserTypesEnum.REGULAR);
        Book book = new Book("aBook",null,10,10,"aAuthor");
        Cd cd=new Cd("And Justice for All",null,10,10,"Metallica");
        try {
            dao.saveUser(admin);
            dao.saveUser(employee);
            dao.saveUser(regularUser);
            dao.saveItem(book);
            dao.saveItem(cd);
        } catch (Exception e){
            System.out.println("fail");
            System.out.println(e.getCause());
        }

    }
    public static void main(String[] args) throws Exception{
        createSampleData();
        DAO dao = DAOFactory.getDaoInstance();

        new ListViewUsers(dao.getAllUsers(),dao.getUser("employee"));
        //new ListViewUsers();
        //new ListViewItems(dao.getAllItems(),dao.getUser("admin"));
        //new LoginMenu();
        System.out.println("tst");
    }
}
