package edu.ijse.dao.custom.impl;

import edu.ijse.dao.CrudUtil;
import edu.ijse.dao.custom.ReturnDao;
import edu.ijse.entity.ReturnEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class ReturnDaoImpl implements ReturnDao {
    @Override
    public boolean create(ReturnEntity returnEntity) throws Exception {
        return CrudUtil.executeUpdate(
                "INSERT INTO `return` VALUES (?,?,?,?,?,?,?)",
                returnEntity.getReturnId(),
                returnEntity.getReturnDate(),
                returnEntity.getFine(),
                returnEntity.getBorrowId(),
                returnEntity.getMemberId(),
                returnEntity.getBookId(),
                returnEntity.isPaid()
        );
    }

    @Override
    public boolean update(ReturnEntity returnEntity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String s) throws Exception {
        return false;
    }

    @Override
    public ReturnEntity get(String s) throws Exception {

        return null;
    }

    @Override
    public ArrayList<ReturnEntity> getAll() throws Exception {
        ArrayList<ReturnEntity> returnEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM `return`");
        while (resultSet.next()) {
            returnEntities.add(new ReturnEntity(
                    resultSet.getString("id"),
                    resultSet.getDate("return_date"),
                    resultSet.getDouble("fine"),
                    resultSet.getString("borrowing_id"),
                    resultSet.getString("borrowing_members_id"),
                    resultSet.getString("borrowing_books_id"),
                    resultSet.getBoolean("is_paid")
            ));
        }
        return returnEntities;
    }

    @Override
    public String getLastID() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM `return` ORDER BY CAST(SUBSTRING(id,3) AS UNSIGNED) DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }
}
