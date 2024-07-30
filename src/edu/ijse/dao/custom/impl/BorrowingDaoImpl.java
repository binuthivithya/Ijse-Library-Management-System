package edu.ijse.dao.custom.impl;

import edu.ijse.dao.CrudUtil;
import edu.ijse.dao.custom.BorrowingDao;
import edu.ijse.entity.BorrowingEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class BorrowingDaoImpl implements BorrowingDao {
    @Override
    public boolean create(BorrowingEntity borrowingEntity) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO borrowing VALUES (?,?,?,?,?,?)",
                borrowingEntity.getBorrowId(), borrowingEntity.getBorrowDate(), borrowingEntity.getDueDate(),
                borrowingEntity.getMemberId(), borrowingEntity.getBookId(), borrowingEntity.isReturned());
    }

    @Override
    public boolean update(BorrowingEntity borrowingEntity) throws Exception {
        return CrudUtil.executeUpdate("UPDATE borrowing SET borrow_date=?,due_date=?,member_id=?,book_id=? WHERE id=?",
                borrowingEntity.getBorrowDate(), borrowingEntity.getDueDate(), borrowingEntity.getMemberId(),
                borrowingEntity.getBookId(), borrowingEntity.getBorrowId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdate("DELETE FROM borrowing WHERE id=?", id);
    }

    @Override
    public BorrowingEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM borrowing WHERE id=?", id);
        if (resultSet.next()) {
            return new BorrowingEntity(
                    resultSet.getString("id"),
                    resultSet.getDate("borrow_date"),
                    resultSet.getDate("due_date"),
                    resultSet.getString("members_id"),
                    resultSet.getString("books_id"),
                    resultSet.getBoolean("is_returned")
            );
        }
        return null;
    }

    @Override
    public ArrayList<BorrowingEntity> getAll() throws Exception {
        ArrayList<BorrowingEntity> borrowingEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM borrowing");
        while (resultSet.next()) {
            BorrowingEntity entity = new BorrowingEntity(
                    resultSet.getString("id"),
                    resultSet.getDate("borrow_date"),
                    resultSet.getDate("due_date"),
                    resultSet.getString("members_id"),
                    resultSet.getString("books_id"),
                    resultSet.getBoolean("is_returned")
            );
            borrowingEntities.add(entity);
        }
        return borrowingEntities;
    }

    @Override
    public String getLastID() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM borrowing ORDER BY CAST(SUBSTRING(id,4) AS UNSIGNED) DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString(1);
        }
        return null;
    }

    @Override
    public ArrayList<BorrowingEntity> search(String searchText) throws Exception {
        ArrayList<BorrowingEntity> borrowingEntities = new ArrayList<>();
        ResultSet resultSetBorrowing = CrudUtil.executeQuery(
                "SELECT * FROM borrowing WHERE id LIKE ? OR members_id LIKE ? OR books_id LIKE ?",
                searchText, searchText, searchText);

        while (resultSetBorrowing.next()) {

            borrowingEntities.add( new BorrowingEntity(
                    resultSetBorrowing.getString("id"),
                    resultSetBorrowing.getDate("borrow_date"),
                    resultSetBorrowing.getDate("due_date"),
                    resultSetBorrowing.getString("members_id"),
                    resultSetBorrowing.getString("books_id"),
                    resultSetBorrowing.getBoolean("is_returned")
            ));
        }
        return borrowingEntities;
    }

    @Override
    public boolean updateReturnStatus(String id) throws Exception {
        return CrudUtil.executeUpdate(
                "UPDATE borrowing SET is_returned= true WHERE id=?", id);
    }
}
