package com.alperovich.fishbook.management.DAO.impl;


import com.alperovich.fishbook.management.DAO.UserDao;
import com.alperovich.fishbook.management.DB.DBConnector;
import com.alperovich.fishbook.management.models.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDaoImpl implements UserDao {


    private User buildUser(ResultSet rs) throws SQLException {
        User user = new User();
        user.setId(rs.getInt("id"));
        user.setFirstName(rs.getString("firstName"));
        user.setLastName(rs.getString("lastName"));
        user.setUserName(rs.getString("userName"));
        user.setPassword(rs.getString("password"));
        return user;

    }

    @Override
    public User findUserById(int id) {
        User user = new User();
        String sql = "SELECT * FROM fishbooking_db.users WHERE users.id = ?";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = buildUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public User findUserByUsername(String username) {
        User user = new User();
        String sql = "SELECT * FROM fishbooking_db.users WHERE users.userName = ?";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                user = buildUser(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    @Override
    public ObservableList<User> getAllUsers() {
        User user;
        ObservableList<User> users = FXCollections.observableArrayList();
        String sql = "SELECT * FROM fishbooking_db.users";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                user = buildUser(rs);
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return users;
    }

    @Override
    public void updateUser(User user) {
        String sql = "UPDATE fishbooking_db.users SET firstName = ?, lastName = ?, userName = ?, password = ? WHERE users.id = ?";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getPassword());
            pstmt.setInt(5, user.getId());
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row is Updated");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void createUser(User user) {
        String sql = "INSERT INTO fishbooking_db.users (firstName, lastName, userName, password) VALUES (?, ?, ?, ?)";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getFirstName());
            pstmt.setString(2, user.getLastName());
            pstmt.setString(3, user.getUserName());
            pstmt.setString(4, user.getPassword());
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row created");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteUserById(int id) {
        String sql = "DELETE FROM fishbooking_db.users WHERE users.id = ?";
        try (Connection conn = DBConnector.getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, id);
            if (pstmt.executeUpdate() == 1) {
                System.out.println("Row Deleted");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}