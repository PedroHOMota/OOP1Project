package com.tus;

import com.tus.dataaccess.DAO;
import com.tus.dataaccess.DAOFactory;
import com.tus.gui.LoginMenu;
import com.tus.items.Book;
import com.tus.items.Cd;
import com.tus.items.Game;
import com.tus.items.GamePlatformsEnum;
import com.tus.items.ItemTypeEnum;
import com.tus.user.AdminUser;
import com.tus.user.EmployeeUser;
import com.tus.user.RegularUser;
import com.tus.user.RegularUserRole;
import com.tus.user.User;
import com.tus.user.UserTypesEnum;

public class Main {

    private static void createSampleData(){
        DAO dao = DAOFactory.getDaoInstance();
        AdminUser admin = new AdminUser("admin","administrator","pass", UserTypesEnum.ADMIN);
        User employee = new EmployeeUser("employee","AnEmployee","pass",UserTypesEnum.EMPLOYEE);
        User regularUser = new RegularUser("regular","AnUser","pass",UserTypesEnum.REGULAR);
        Book book = new Book("Lord of the Rings","01/01/2001",10,10,"J.R.R Tolkien",ItemTypeEnum.BOOK);
        Book book2 = new Book("King of Thorns","01/01/2001",10,10,"Mark Lawrance",ItemTypeEnum.BOOK);
        Book book3 = new Book("Sophie's World","01/01/2001",10,10,"Jostein Gaarder",ItemTypeEnum.BOOK);

        Game game = new Game("F1 2012","01/01/2001",10,10, GamePlatformsEnum.XBOX360, ItemTypeEnum.GAME);
        Game game2 = new Game("Final Fantasy XIII","01/01/2001",10,10, GamePlatformsEnum.PS3, ItemTypeEnum.GAME);
        Game game3 = new Game("Super Mario Galaxy","01/01/2001",10,10, GamePlatformsEnum.WII, ItemTypeEnum.GAME);
        Cd cd=new Cd("And Justice for All","01/01/2001",10,10,"Metallica", ItemTypeEnum.CD);
        Cd cd2=new Cd("Viva the underdogs","01/01/2001",10,10,"Parkway Drive", ItemTypeEnum.CD);
        Cd cd3=new Cd("Rebirth","01/01/2001",10,10,"Angra", ItemTypeEnum.CD);
        Cd cd4=new Cd("Vera Cruz","01/01/2001",10,10,"Edu Falaschi", ItemTypeEnum.CD);
        Cd cd5=new Cd("To Live Again","01/01/2001",10,10,"Viper", ItemTypeEnum.CD);
        try {

            dao.saveUser(admin);
            dao.saveUser(employee);
            dao.saveUser(regularUser);
            dao.saveItem(book);
            dao.saveItem(book2);
            dao.saveItem(book3);
            dao.saveItem(cd);
            dao.saveItem(cd2);
            dao.saveItem(cd3);
            dao.saveItem(cd4);
            dao.saveItem(cd5);
            dao.saveItem(game);
            dao.saveItem(game2);
            dao.saveItem(game3);

            ((RegularUserRole) regularUser).borrowItem(game.getName(),game.getItemType());
            ((RegularUserRole) regularUser).borrowItem(cd.getName(),cd.getItemType());
            ((RegularUserRole) regularUser).borrowItem(cd2.getName(),cd2.getItemType());
            ((RegularUserRole) regularUser).borrowItem(cd3.getName(),cd3.getItemType());
            ((RegularUserRole) regularUser).borrowItem(book.getName(),book.getItemType());
            ((RegularUserRole) regularUser).borrowItem(game2.getName(),game.getItemType());
        } catch (Exception e){
            System.out.println("fail");
            e.printStackTrace();
        }

    }
    public static void main(String[] args) throws Exception{
        createSampleData();

        new LoginMenu();
    }
}
