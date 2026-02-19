package com.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.db.MyConnection;
import com.model.User;

public class userdao {

    public static boolean isExist(String email) throws SQLException {

        Connection connection = MyConnection.getConnection();

        PreparedStatement ps = connection.prepareStatement(
                "SELECT email FROM user WHERE email = ?"
        );

        ps.setString(1, email);

        ResultSet rs = ps.executeQuery();

        return rs.next();
    }

    public static int saveUser(User user) throws SQLException {

        Connection connection = MyConnection.getConnection();

        PreparedStatement ps = connection.prepareStatement(
                "INSERT INTO user (name, email) VALUES (?, ?)"
        );

        ps.setString(1, user.getName());
        ps.setString(2, user.getEmail());

        return ps.executeUpdate();
    }
}