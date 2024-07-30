package edu.ijse.service;

import edu.ijse.service.custom.impl.*;

public class ServiceFactory {
    private static ServiceFactory serviceFactory;

    public static ServiceFactory getInstance() {
        if (serviceFactory == null) {
            serviceFactory = new ServiceFactory();
        }
        return serviceFactory;
    }

    public  SuperService getService(ServiceType type) {
        switch (type) {
            case USER:
                return new UserServiceImpl();
            case CATEGORY:
                return new CategoryServiceImpl();
            case BOOK:
                return new BookServiceImpl();
            case MEMBER:
                return new MemberServiceImpl();
            case BORROWING:
              //  return new BorrowingServiceImpl();
            case RETURN:
               // return new ReturnServiceImpl();
            default:
                return null;
        }
    }

    public enum ServiceType {
        USER, CATEGORY, BOOK, MEMBER, BORROWING, RETURN
    }
}
