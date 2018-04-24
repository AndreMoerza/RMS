/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mitrais.rmsweb.dao;

import java.sql.*;
import java.util.*;
import com.mitrais.rmsweb.model.User;
import com.mitrais.rmsweb.util.Database;
/**
 *
 * @author Andre_P772
 */
public class UserDao {
    private Connection connection;
 
    public UserDao() {
        connection = Database.getConnection();
    }
 
    public void checkUser(User user) {
        try {
            PreparedStatement ps = connection.prepareStatement("select uname from users where uname = ?");
            ps.setString(1, user.getuname());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) // found
            {
                updateUser(user);
            } else {
                addUser(user);
            }
        } catch (Exception ex) {
            System.out.println("Error in check() -->" + ex.getMessage());
        } 
    }
    public void addUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("insert into users(uname, password, email, registeredon) values (?, ?, ?, ? )");
            // Parameters start with 1
            preparedStatement.setString(1, user.getuname());
            preparedStatement.setString(2, user.getpassword());
            preparedStatement.setString(3, user.getemail());            
            preparedStatement.setDate(4, new java.sql.Date(user.getregisteredon().getTime()));
            preparedStatement.executeUpdate();
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    public void deleteUser(String userId) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("delete from users where uname=?");
            // Parameters start with 1
            preparedStatement.setString(1, userId);
            preparedStatement.executeUpdate();
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    public void updateUser(User user) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("update users set password=?, email=?, registeredon=?"
                    + "where uname=?");
            // Parameters start with 1
            System.out.println(new java.sql.Date(user.getregisteredon().getTime()));
            preparedStatement.setString(1, user.getpassword());
            preparedStatement.setString(2, user.getemail());
            preparedStatement.setDate(3, new java.sql.Date(user.getregisteredon().getTime()));
            preparedStatement.setString(4, user.getuname());
            preparedStatement.executeUpdate();
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    public List<User> getAllUsers() {
        List<User> users = new ArrayList<User>();
        try {
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery("select * from users");
            while (rs.next()) {
                User user = new User();
                user.setuname(rs.getString("uname"));
                user.setpassword(rs.getString("password"));
                user.setemail(rs.getString("email"));
                user.setregisteredon(rs.getDate("registeredon"));
                users.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return users;
    }
 
    public User getUserById(String userId) {
        User user = new User();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("select * from users where uname=?");
            preparedStatement.setString(1, userId);
            ResultSet rs = preparedStatement.executeQuery();
 
            if (rs.next()) {
                user.setuname(rs.getString("uname"));
                user.setpassword(rs.getString("password"));
                user.setemail(rs.getString("email"));
                user.setregisteredon(rs.getDate("registeredon"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
 
        return user;
    }
}
