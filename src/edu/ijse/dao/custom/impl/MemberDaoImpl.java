package edu.ijse.dao.custom.impl;

import edu.ijse.dao.CrudUtil;
import edu.ijse.dao.custom.MemberDao;
import edu.ijse.entity.MemberEntity;

import java.sql.ResultSet;
import java.util.ArrayList;

public class MemberDaoImpl implements MemberDao {
    @Override
    public boolean create(MemberEntity memberEntity) throws Exception {
        return CrudUtil.executeUpdate("INSERT INTO members VALUES (?,?,?,?,?,?)",
                memberEntity.getMemberID(), memberEntity.getName(), memberEntity.getAddress(), memberEntity.getContact(),
                memberEntity.getEmail(), memberEntity.getMembershipDate());
    }

    @Override
    public boolean update(MemberEntity memberEntity) throws Exception {
        return CrudUtil.executeUpdate("UPDATE members SET name=?,address=?,phone=?,email=?,membership_date=? WHERE id=?",
                memberEntity.getName(), memberEntity.getAddress(), memberEntity.getContact(), memberEntity.getEmail(),
                memberEntity.getMembershipDate(), memberEntity.getMemberID());
    }

    @Override
    public boolean delete(String id) throws Exception {
        return CrudUtil.executeUpdate("DELETE FROM members WHERE id=?", id);
    }

    @Override
    public MemberEntity get(String id) throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM members WHERE id=?", id);
        if (resultSet.next()) {
            return new MemberEntity(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getDate("membership_date")
            );
        }
        return null;
    }

    @Override
    public ArrayList<MemberEntity> getAll() throws Exception {
        ArrayList<MemberEntity> memberEntities = new ArrayList<>();
        ResultSet resultSet = CrudUtil.executeQuery("SELECT * FROM members");
        while (resultSet.next()) {
            MemberEntity entity = new MemberEntity(
                    resultSet.getString("id"),
                    resultSet.getString("name"),
                    resultSet.getString("address"),
                    resultSet.getString("phone"),
                    resultSet.getString("email"),
                    resultSet.getDate("membership_date")
            );
            memberEntities.add(entity);
        }
        return memberEntities;
    }

    @Override
    public String getLastID() throws Exception {
        ResultSet resultSet = CrudUtil.executeQuery(
                "SELECT id FROM members ORDER BY CAST(SUBSTRING(id,3) AS UNSIGNED) DESC LIMIT 1");
        if (resultSet.next()) {
            return resultSet.getString("id");
        }
        return null;
    }
}
