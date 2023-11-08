package org.example.services;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;

@WebService
public class FollowingService {
    @WebMethod
    public String requestFollow(String creatorID, String followerID, String creatorName, String followerName) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            PreparedStatement statement = connection.prepareStatement("INSERT INTO following (creatorID, followerID, creatorName, followerName, status) VALUES(?, ?, ?, ?, ?)");
            statement.setString(1, creatorID);
            statement.setString(2, followerID);
            statement.setString(3, creatorName);
            statement.setString(4, followerName);
            statement.setString(5, "PENDING");
            statement.execute();
            connection.close();
            return "Succeed";
        }catch (SQLException error){
            return error.getMessage();
        }
    }

    @WebMethod
    public String confirmFollow(String creatorID, String followerID, Boolean isApproved){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            PreparedStatement statement = connection.prepareStatement("UPDATE following SET status = ? WHERE creatorID = ? and followerID = ?");
            statement.setString(2, creatorID);
            statement.setString(3, followerID);
            if(isApproved){
                statement.setString(1, "APPROVED");
            }else{
                statement.setString(1, "REJECTED");
            }
            statement.execute();
            connection.close();
            return "Succeed";
        }catch (SQLException error){
            return error.getMessage();
        }
    }

    @WebMethod
    public String getFollowersByID(String creatorID, Integer page, Integer perpage){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM following WHERE creatorID = ? AND status = 'APPROVED' LIMIT ? OFFSET ?");
            statement.setString(1, creatorID);
            statement.setInt(2, perpage);
            statement.setInt(3, (page.intValue()-1)*perpage.intValue());
            ResultSet res = statement.executeQuery();
            StringBuilder jsonString = new StringBuilder();
            jsonString.append("[");
            while (res.next()){
                jsonString.append("{\"creatorID\": ").append(res.getString("creatorID")).append(",");
                jsonString.append("\"followerID\": ").append(res.getString("followerID")).append(",");
                jsonString.append("\"creatorName\": \"").append(res.getString("creatorName")).append("\",");
                jsonString.append("\"followerName\": \"").append(res.getString("followerName")).append("\"}");
                if(!res.isLast()){
                    jsonString.append(", ");
                }
            }
            jsonString.append("]");
            connection.close();
            return jsonString.toString();
        }catch (SQLException error){
            return error.getMessage();
        }
    }

    @WebMethod
    public String getFollowersCountByID(String creatorID){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS cnt FROM following WHERE creatorID = ? AND status = 'APPROVED'");
            statement.setString(1, creatorID);
            ResultSet res = statement.executeQuery();
            StringBuilder jsonString = new StringBuilder();
            res.next();
            jsonString.append("{\"count\":").append(res.getString("cnt")).append("}");
            connection.close();
            return jsonString.toString();
        }catch (SQLException error){
            return error.getMessage();
        }
    }

    @WebMethod
    public String getFollowingsByID(String followerID, Integer page, Integer perpage){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            PreparedStatement statement = connection.prepareStatement("SELECT * FROM following WHERE followerID = ? AND status = 'APPROVED' LIMIT ? OFFSET ?");
            statement.setString(1, followerID);
            statement.setInt(2, perpage);
            statement.setInt(3, (page.intValue()-1)*perpage.intValue());
            ResultSet res = statement.executeQuery();
            StringBuilder jsonString = new StringBuilder();
            jsonString.append("[");
            while (res.next()){
                jsonString.append("{\"creatorID\": ").append(res.getString("creatorID")).append(",");
                jsonString.append("\"followerID\": ").append(res.getString("followerID")).append(",");
                jsonString.append("\"creatorName\": \"").append(res.getString("creatorName")).append("\",");
                jsonString.append("\"followerName\": \"").append(res.getString("followerName")).append("\"}");
                if(!res.isLast()){
                    jsonString.append(", ");
                }
            }
            jsonString.append("]");
            connection.close();
            return jsonString.toString();
        }catch (SQLException error){
            return error.getMessage();
        }
    }

    @WebMethod
    public String getFollowingsCountByID(String followerID){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS cnt FROM following WHERE followerID = ? AND status = 'APPROVED'");
            statement.setString(1, followerID);
            ResultSet res = statement.executeQuery();
            StringBuilder jsonString = new StringBuilder();
            res.next();
            jsonString.append("{\"count\":").append(res.getString("cnt")).append("}");
            connection.close();
            return jsonString.toString();
        }catch (SQLException error){
            return error.getMessage();
        }
    }

    public boolean isFollowed(String creatorID, String followerID){
        try {
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            PreparedStatement statement = connection.prepareStatement("SELECT COUNT(*) AS cnt FROM following WHERE followerID = ? AND creatorID = ? AND status = 'APPROVED'");
            statement.setString(1, followerID);
            statement.setString(2, creatorID);
            ResultSet res = statement.executeQuery();
            res.next();
            Boolean result;
            result = res.getInt("cnt") == 1;
            connection.close();
            return result;
        }catch (SQLException error){
            System.out.println(error.getMessage());
            return false;
        }
    }

    @WebMethod
    public String getContent(String creatorID, String followerID){
        if(isFollowed(creatorID, followerID)){
            try{
                URL url = new URL("rest_url");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("GET");
                http.setDoOutput(true);
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                System.out.println("Respons: " + response.toString());
                http.disconnect();
                return "";
            }catch (IOException error) {
                return error.getMessage();
            }
        }else{
            return "Not Followed";
        }
    }
}
