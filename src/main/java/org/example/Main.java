package org.example;

import com.sun.net.httpserver.HttpContext;
import com.sun.net.httpserver.HttpServer;
import org.example.middlewares.Cors;
import org.example.middlewares.Logger;
import org.example.services.FollowingService;

import javax.xml.ws.handler.Handler;
import javax.xml.ws.Endpoint;
import java.net.InetSocketAddress;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            DriverManager.registerDriver(new com.mysql.cj.jdbc.Driver());
            Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3307/mydatabase", "root", "mysecretpassword");
            Statement statement = connection.createStatement();
            statement.executeUpdate("create table if not exists following (creatorID varchar(50) not null, followerID varchar(50) not null, creatorName varchar(100), followerName varchar(100), status varchar(10) not null)");
            statement.executeUpdate("create table if not exists log (ID int not null auto_increment, description varchar(256), IP varchar(50), endpoint varchar(50), requestedAt datetime default NOW(), PRIMARY KEY (ID))");
            System.out.println("berhasil");
            connection.close();

            HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);

            HttpContext context = server.createContext("/api/following");

            Endpoint endpoint = Endpoint.create(new FollowingService());
            List<Handler> handlerChain = new ArrayList<>();
            handlerChain.add(new Logger());
            endpoint.getBinding().setHandlerChain(handlerChain);
            endpoint.publish(context);

            context.getFilters().add(new Cors());
            server.start();
            System.out.println("Service running at http://localhost:8000/api/following");
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}