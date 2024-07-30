package edu.ijse.dao.custom;

import edu.ijse.dao.CrudDao;
import edu.ijse.entity.BorrowingEntity;

import java.util.ArrayList;

public interface BorrowingDao extends CrudDao<BorrowingEntity,String> {
    ArrayList<BorrowingEntity> search(String searchText) throws Exception;
    boolean updateReturnStatus(String id) throws Exception;
}
