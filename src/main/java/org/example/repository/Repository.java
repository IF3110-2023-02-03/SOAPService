package org.example.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Repository {

    private final String dbUrl = "jdbc:mysql://host.docker.internal:3308/mydatabase";
    private final String dbUsername = "root";
    private final String dbPassword = "mysecretpassword";

    private Connection getConnection() throws SQLException {
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

    public ResultSet getFollowers(String creatorID, int limit, int offset, String filter) throws SQLException {
        if(filter.length() > 0){
            Connection connection = getConnection();
            StringBuilder query = new StringBuilder("SELECT * FROM following WHERE creatorID = ? AND status = 'APPROVED' AND (followerUsername LIKE '%");
            query.append(filter).append("%' OR followerName LIKE '%").append(filter).append("%') LIMIT ? OFFSET ?");
            System.out.println(query.toString());
            PreparedStatement statement = connection.prepareStatement(query.toString());
            statement.setString(1, creatorID);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            System.out.println(statement);
            return statement.executeQuery();
        }else{
            
            Connection connection = getConnection();
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM following WHERE creatorID = ? AND status = 'APPROVED' LIMIT ? OFFSET ?");
            statement.setString(1, creatorID);
            statement.setInt(2, limit);
            statement.setInt(3, offset);
            System.out.println(statement);
            return statement.executeQuery();
        }
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

    public void updateFollowerUsername(String oldFollowerUsername, String newFollowerUsername) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE following SET followerUsername = ? WHERE followerUsername = ?");
        statement.setString(1, newFollowerUsername);
        statement.setString(2, oldFollowerUsername);
        statement.execute();
        connection.close();
    }

    public void updateCreatorUsername(String oldCreatorUsername, String newCreatorUsername) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE following SET creatorUsername = ? WHERE creatorUsername = ?");
        statement.setString(1, newCreatorUsername);
        statement.setString(2, oldCreatorUsername);
        statement.execute();
        connection.close();
    }

    public void updateFollowerName(String oldFollowerName, String newFollowerName) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE following SET followerName = ? WHERE followerName = ?");
        statement.setString(1, newFollowerName);
        statement.setString(2, oldFollowerName);
        statement.execute();
        connection.close();
    }

    public void updateCreatorName(String oldCreatorName, String newCreatorName) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement statement = connection.prepareStatement("UPDATE following SET creatorName = ? WHERE creatorName = ?");
        statement.setString(1, newCreatorName);
        statement.setString(2, oldCreatorName);
        statement.execute();
        connection.close();
    }
}