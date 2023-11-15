package org.example.services;

import org.example.repository.Repository;
import org.json.JSONObject;
import org.json.JSONArray;

import javax.jws.WebMethod;
import javax.jws.WebService;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.*;
import java.util.Objects;

@WebService
public class FollowingService {
    private Repository repository = new Repository();
    @WebMethod
    public String requestFollow(String creatorID, String followerID, String creatorName, String followerName, String creatorUsername, String followerUsername, String api_key) {
        if(Objects.equals(api_key, "ini_api_key_monolitik")){
            try {
                ResultSet res = repository.getFollowingStatus(creatorID, followerID);
                if(!res.next()){
                    repository.insertOrUpdateFollowing(creatorID, followerID, creatorName, followerName, creatorUsername, followerUsername, "PENDING");
                }else{
                    repository.updateFollowingStatus(creatorID, followerID, "PENDING");
                }
                return "Success";
            }catch (SQLException error){
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String confirmFollow(String creatorID, String followerID, Boolean isApproved, String api_key){
        if(Objects.equals(api_key, "ini_api_key_rest")) {
            try {
                if (isApproved) {
                    repository.updateFollowingStatus(creatorID, followerID, "APPROVED");
                } else {
                    repository.updateFollowingStatus(creatorID, followerID, "REJECTED");
                }
                return "Success";
            } catch (SQLException error) {
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String getFollowersByID(String creatorID, Integer page, Integer perpage, String api_key){
        if(Objects.equals(api_key, "ini_api_key_rest")) {
            try {
                ResultSet res = repository.getFollowers(creatorID, perpage, (page - 1) * perpage);
                StringBuilder jsonString = new StringBuilder();
                jsonString.append("[");
                while (res.next()) {
                    jsonString.append("{\"creatorID\": ").append(res.getString("creatorID")).append(",");
                    jsonString.append("\"followerID\": ").append(res.getString("followerID")).append(",");
                    jsonString.append("\"creatorName\": \"").append(res.getString("creatorName")).append("\",");
                    jsonString.append("\"followerName\": \"").append(res.getString("followerName")).append("\",");
                    jsonString.append("\"creatorUsername\": \"").append(res.getString("creatorUsername")).append("\",");
                    jsonString.append("\"followerUsername\": \"").append(res.getString("followerUsername")).append("\"}");
                    if (!res.isLast()) {
                        jsonString.append(", ");
                    }
                }
                jsonString.append("]");
                return jsonString.toString();
            } catch (SQLException error) {
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String getFollowersCountByID(String creatorID, String api_key){
        if(Objects.equals(api_key, "ini_api_key_rest")) {
            try {
                ResultSet res = repository.getFollowersCount(creatorID);
                StringBuilder jsonString = new StringBuilder();
                res.next();
                jsonString.append("{\"count\":").append(res.getString("cnt")).append("}");
                return jsonString.toString();
            } catch (SQLException error) {
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String getFollowingsByID(String followerID, Integer page, Integer perpage, String api_key){
        if(Objects.equals(api_key, "ini_api_key_monolitik")) {
            try {
                ResultSet res = repository.getFollowings(followerID, perpage, (page - 1) * perpage);
                StringBuilder jsonString = new StringBuilder();
                jsonString.append("[");
                while (res.next()) {
                    jsonString.append("{\"creatorID\": ").append(res.getString("creatorID")).append(",");
                    jsonString.append("\"followerID\": ").append(res.getString("followerID")).append(",");
                    jsonString.append("\"creatorName\": \"").append(res.getString("creatorName")).append("\",");
                    jsonString.append("\"followerName\": \"").append(res.getString("followerName")).append("\"}");
                    if (!res.isLast()) {
                        jsonString.append(", ");
                    }
                }
                jsonString.append("]");
                return jsonString.toString();
            } catch (SQLException error) {
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String getFollowingsCountByID(String followerID, String api_key){
        if(Objects.equals(api_key, "ini_api_key_monolitik")) {
            try {
                ResultSet res = repository.getFollowingsCount(followerID);
                StringBuilder jsonString = new StringBuilder();
                res.next();
                jsonString.append("{\"count\":").append(res.getString("cnt")).append("}");
                return jsonString.toString();
            } catch (SQLException error) {
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String getPendingFollowingsByID(String creatorID, Integer page, Integer perpage, String api_key){
        if(Objects.equals(api_key, "ini_api_key_rest")) {
            try {
                ResultSet res = repository.getPendingFollowings(creatorID, perpage, (page - 1) * perpage);
                StringBuilder jsonString = new StringBuilder();
                jsonString.append("[");
                while (res.next()) {
                    jsonString.append("{\"creatorID\": ").append(res.getString("creatorID")).append(",");
                    jsonString.append("\"followerID\": ").append(res.getString("followerID")).append(",");
                    jsonString.append("\"creatorName\": \"").append(res.getString("creatorName")).append("\",");
                    jsonString.append("\"followerName\": \"").append(res.getString("followerName")).append("\",");
                    jsonString.append("\"creatorUsername\": \"").append(res.getString("creatorUsername")).append("\",");
                    jsonString.append("\"followerUsername\": \"").append(res.getString("followerUsername")).append("\"}");
                    if (!res.isLast()) {
                        jsonString.append(", ");
                    }
                }
                jsonString.append("]");
                return jsonString.toString();
            } catch (SQLException error) {
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String getPendingFollowingsCountByID(String creatorID, String api_key){
        if(Objects.equals(api_key, "ini_api_key_rest")) {
            try {
                ResultSet res = repository.getPendingFollowingsCount(creatorID);
                StringBuilder jsonString = new StringBuilder();
                res.next();
                jsonString.append("{\"count\":").append(res.getString("cnt")).append("}");
                return jsonString.toString();
            } catch (SQLException error) {
                return error.getMessage();
            }
        }else {
            return "Unauthorized";
        }
    }

    public boolean isFollowed(String creatorID, String followerID){
        try {
            return repository.isFollowed(creatorID, followerID);
        } catch (SQLException error) {
            return false;
        }
    }

    @WebMethod
    public String getContent(String followerID, Integer page, Integer perpage, String api_key){
        if(Objects.equals(api_key, "ini_api_key_monolitik")) {
            try {
                ResultSet res = repository.getIds(followerID);
                StringBuilder body = new StringBuilder("{ \"ids\": [");
                while (res.next()){
                    body.append(res.getInt("creatorID"));
                    if(!res.isLast()){
                        body.append(", ");
                    }
                }
                body.append("], \"page\": ").append(page).append(", \"perpage\": ").append(perpage).append("}");

                URL url = new URL("http://localhost:3000/api/contents");
                HttpURLConnection http = (HttpURLConnection) url.openConnection();
                http.setRequestMethod("POST");
                http.setDoOutput(true);
                http.setRequestProperty("Content-Type", "application/json");
                try (OutputStream os = http.getOutputStream()) {
                    byte[] input = body.toString().getBytes("utf-8");
                    os.write(input, 0, input.length);
                }
                BufferedReader reader = new BufferedReader(new InputStreamReader(http.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();
                System.out.println(response);
                http.disconnect();
                return response.toString();
            } catch (IOException | SQLException error) {
                return error.getMessage();
            }
        }else{
            return "Unauthorized";
        }
    }

    @WebMethod
    public String getContentCreators(Integer page, Integer perpage, String filter, String id, String api_key){
        if(Objects.equals(api_key, "ini_api_key_monolitik")) {
            try {
                StringBuilder path = new StringBuilder("http://localhost:3000/api/user?page=");
                path.append(page.toString()).append("&perpage=").append(perpage.toString()).append("&filter=").append(filter);
                URL url = new URL(path.toString());
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
                http.disconnect();

                JSONObject object = new JSONObject(response.toString());
                JSONArray array = object.getJSONArray("data");
                for(int i=0;i<array.length();i++){
                    ResultSet res = repository.getFollowingStatus(array.getJSONObject(i).getString("userID"), id);
                    if(res.next()){
                        array.getJSONObject(i).put("status", res.getString("status"));
                    }else{
                        array.getJSONObject(i).put("status", "REJECTED");
                    }
                }
                return array.toString();
            } catch (IOException | SQLException error) {
                return "Error";
            }
        }else{
            return "Unauthorized";
        }
    }
}
