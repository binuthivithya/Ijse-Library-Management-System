package edu.ijse.dao;

import edu.ijse.dao.custom.impl.*;

public class DaoFactory {
    private static DaoFactory daoFactory;

    private DaoFactory() {}

    public static DaoFactory getInstance() {
        if (daoFactory == null) {
            daoFactory = new DaoFactory();
        }
        return daoFactory;
    }

    public SuperDao getDao(DaoType type){
        switch (type){
            case USER:
                return new UserDaoImpl();
            case CATEGORY:
                return new CategoryDaoImpl();
            case BOOK:
                //return new BookDaoImpl();
            case MEMBER:
               // return new MemberDaoImpl();
            case BORROWING:
                //return new BorrowingDaoImpl();
            case RETURN:
                //return new ReturnDaoImpl();
            default:
                return null;
        }
    }

    public enum DaoType {
        USER, BOOK, CATEGORY, MEMBER, BORROWING, RETURN
    }
}
