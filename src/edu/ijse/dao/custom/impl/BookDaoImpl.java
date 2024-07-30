package edu.ijse.dao.custom.impl;

import edu.ijse.dao.CrudUtil;
import edu.ijse.dao.custom.BookDao;
import edu.ijse.entity.BookEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class BookDaoImpl implements BookDao {
    @Override
    public boolean create(BookEntity bookEntity) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO books VALUES (?,?,?,?,?,?,?)",
                bookEntity.getBookID(),bookEntity.getTitle(),bookEntity.getAuthor(),
                bookEntity.getIsbn(),bookEntity.getQoh(),bookEntity.getCategoryID(),bookEntity.getUnitPrice());
    }

    @Override
    public boolean update(BookEntity bookEntity) throws Exception {
        return CrudUtil.executeUpdate(
                "UPDATE books SET title=?,author=?,isbn=?,qoh=?,book_categories_category_id=?,unit_price=? WHERE id=?",
                bookEntity.getTitle(),bookEntity.getAuthor(),bookEntity.getIsbn(),bookEntity.getQoh(),
                bookEntity.getCategoryID(),bookEntity.getUnitPrice(),bookEntity.getBookID());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdate("DELETE FROM books WHERE id=?",id);
    }

    @Override
    public BookEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM books WHERE id=?", id);
        if(resultSet.next()){
            return new BookEntity(
                    resultSet.getString("id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("isbn"),
                    resultSet.getInt("qoh"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getString("book_categories_category_id")

            );
        }
        return null;
    }

    @Override
    public ArrayList<BookEntity> getAll() throws Exception {
        ArrayList<BookEntity> bookEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM books");
        while (resultSet.next()){
            BookEntity entity = new BookEntity(
                    resultSet.getString("id"),
                    resultSet.getString("title"),
                    resultSet.getString("author"),
                    resultSet.getString("isbn"),
                    resultSet.getInt("qoh"),
                    resultSet.getDouble("unit_price"),
                    resultSet.getString("book_categories_category_id")
            );
            bookEntities.add(entity);
        }
        return bookEntities;
    }

    @Override
    public String getLastID() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT id FROM books ORDER BY CAST(SUBSTRING(id,3) AS UNSIGNED) DESC LIMIT 1");
        if(resultSet.next()){
            return resultSet.getString("id");
        }
        return null;
    }
}
