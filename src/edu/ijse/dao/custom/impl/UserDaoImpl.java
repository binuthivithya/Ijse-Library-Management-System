package edu.ijse.dao.custom.impl;

import edu.ijse.dao.CrudUtil;
import edu.ijse.dao.custom.UserDao;
import edu.ijse.entity.UserEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class UserDaoImpl implements UserDao {
    @Override
    public boolean create(UserEntity t) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO user VALUES(?,?,?,?)",
                t.getEmail(), t.getFirstName(), t.getLastName(), t.getPassword());
    }

    @Override
    public boolean update(UserEntity userEntity) throws Exception {
        return false;
    }

    @Override
    public boolean delete(String id) throws Exception {
        return false;
    }

    @Override
    public UserEntity get(String email) throws Exception {
        ResultSet resultSet =  CrudUtil.executeQuery("SELECT * FROM user WHERE email = ?", email);
        if (resultSet.next()) {
            return new UserEntity(
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("password")
            );
        }
        return null;
    }

    @Override
    public ArrayList<UserEntity> getAll() throws Exception {
        ArrayList<UserEntity> userEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM user");
        while(resultSet.next()){
            UserEntity entity = new UserEntity(
                    resultSet.getString("email"),
                    resultSet.getString("first_name"),
                    resultSet.getString("last_name"),
                    resultSet.getString("password")
            );
            userEntities.add(entity);
        }
        return userEntities;
    }

    @Override
    public String getLastID() throws Exception {
        return "";
    }
}
