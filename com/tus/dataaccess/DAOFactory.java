package com.tus.dataaccess;

final public class DAOFactory{
    private static DAO dao;
    public static DAO getDaoInstance(){
        if(dao == null){
            dao = new DAO();
        }

        return dao;
    }
}
