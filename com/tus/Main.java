//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by FernFlower decompiler)
//

package com.tus;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.gui.ListView;
import com.tus.gui.LoginMenu;
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
        try {
            dao.saveUser(admin);
            dao.saveUser(employee);
            dao.saveUser(regularUser);
        } catch (Exception e){
            System.out.println("fail");
            System.out.println(e.getCause());
        }

    }
    public static void main(String[] args) {
        createSampleData();
        DAO dao = DAOFactory.getDaoInstance();
        //new ListView(dao.getAllUsers());
        new LoginMenu();
        System.out.println("tst");
    }
}
