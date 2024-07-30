package edu.ijse.dao.custom.impl;

import edu.ijse.dao.CrudUtil;
import edu.ijse.dao.custom.CategoryDao;
import edu.ijse.entity.CategoryEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class CategoryDaoImpl implements CategoryDao {
    @Override
    public boolean create(CategoryEntity categoryEntity) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO book_categories VALUES (?,?,?)",
                categoryEntity.getCatId(),categoryEntity.getCatName(),categoryEntity.getCatDesc());
    }

    @Override
    public boolean update(CategoryEntity categoryEntity) throws Exception {
        return CrudUtil.executeUpdate("UPDATE book_categories SET name=?, description=? WHERE category_id=?",
                categoryEntity.getCatName(),categoryEntity.getCatDesc(),categoryEntity.getCatId());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdate("DELETE FROM book_categories WHERE category_id=?", id);
    }

    @Override
    public CategoryEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM book_categories WHERE category_id=?", id);
        if (resultSet.next()) {
            return new CategoryEntity(
                    resultSet.getString("category_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description")
            );
        }
        return null;
    }

    @Override
    public ArrayList<CategoryEntity> getAll() throws Exception {
        ArrayList<CategoryEntity> categoryEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM book_categories");

        while (resultSet.next()) {
            CategoryEntity entity = new CategoryEntity(
                    resultSet.getString("category_id"),
                    resultSet.getString("name"),
                    resultSet.getString("description")
            );
            categoryEntities.add(entity);
        }
        return categoryEntities;
    }

    public String getLastID() throws Exception {
        ResultSet rs = CrudUtil.executeQuery("SELECT category_id FROM book_categories ORDER BY CAST(SUBSTRING(category_id,3) AS UNSIGNED ) DESC LIMIT 1");
        if (rs.next()) {
            return rs.getString("category_id");
        }
        return null;
    }
}
