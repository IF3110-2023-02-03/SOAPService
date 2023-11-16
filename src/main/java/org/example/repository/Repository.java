package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository {

    private final String dbUrl = "jdbc:mysql://host.docker.internal:3307/mydatabase";
    private final String dbUsername = "root";
    private final String dbPassword = "mysecretpassword";

    private Connection getConnection() throws SQLException {
        System.out.println("siniiiii");
        return DriverManager.getConnection(dbUrl, dbUsername, dbPassword);
    }

    public ResultSet getFollowingStatus(String creatorID, String followerID) throws SQLException {
        Connection connection = getConnection();
        System.out.println("sini 2");
        PreparedStatement statement = connection.prepareStatement("SELECT status FROM following WHERE creatorID = ? AND followerID = ?");
        statement.setString(1, creatorID);
        statement.setString(2, followerID);
        System.out.println("sini3");
        return statement.executeQuery();
    }

    public void insertOrUpdateFollowing(String creatorID, String followerID, String creatorName, String followerName, String creatorUsername, String followerUsername, String status) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("INSERT INTO following (creatorID, followerID, creatorName, followerName, creatorUsername, followerUsername, status) VALUES(?, ?, ?, ?, ?, ?, ?) ON DUPLICATE KEY UPDATE status = ?");
        statement.setString(1, creatorID);
        statement.setString(2, followerID);
        statement.setString(3, creatorName);
        statement.setString(4, followerName);
        statement.setString(5, creatorUsername);
        statement.setString(6, followerUsername);
        statement.setString(7, status);
        statement.setString(8, status);
        statement.execute();
        connection.close();
    }

    public void updateFollowingStatus(String creatorID, String followerID, String status) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE following SET status = ? WHERE creatorID = ? AND followerID = ?");
        statement.setString(1, status);
        statement.setString(2, creatorID);
        statement.setString(3, followerID);
        statement.execute();
        connection.close();
    }

    public ResultSet getFollowers(String creatorID, int limit, int offset) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM following WHERE creatorID = ? AND status = 'APPROVED' LIMIT ? OFFSET ?");
        statement.setString(1, creatorID);
        statement.setInt(2, limit);
        statement.setInt(3, offset);
        return statement.executeQuery();
    }

    public ResultSet getFollowersCount(String creatorID) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS cnt FROM following WHERE creatorID = ? AND status = 'APPROVED'");
        statement.setString(1, creatorID);
        return statement.executeQuery();
    }

    public ResultSet getFollowings(String followerID, int limit, int offset) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM following WHERE followerID = ? AND status = 'APPROVED' LIMIT ? OFFSET ?");
        statement.setString(1, followerID);
        statement.setInt(2, limit);
        statement.setInt(3, offset);
        return statement.executeQuery();
    }

    public ResultSet getFollowingsCount(String followerID) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS cnt FROM following WHERE followerID = ? AND status = 'APPROVED'");
        statement.setString(1, followerID);
        return statement.executeQuery();
    }

    public ResultSet getPendingFollowings(String creatorID, int limit, int offset) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM following WHERE creatorID = ? AND status = 'PENDING' LIMIT ? OFFSET ?");
        statement.setString(1, creatorID);
        statement.setInt(2, limit);
        statement.setInt(3, offset);
        return statement.executeQuery();
    }

    public ResultSet getPendingFollowingsCount(String creatorID) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS cnt FROM following WHERE creatorID = ? AND status = 'PENDING'");
        statement.setString(1, creatorID);
        return statement.executeQuery();
    }

    public boolean isFollowed(String creatorID, String followerID) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS cnt FROM following WHERE followerID = ? AND creatorID = ? AND status = 'APPROVED'");
        statement.setString(1, followerID);
        statement.setString(2, creatorID);
        ResultSet res = statement.executeQuery();
        res.next();
        boolean result = res.getInt("cnt") == 1;
        connection.close();
        return result;
    }

    public ResultSet getIds(String followerID) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("SELECT creatorID FROM following WHERE followerID = ? AND status = 'APPROVED'");
        statement.setString(1, followerID);
        return statement.executeQuery();
    }
}