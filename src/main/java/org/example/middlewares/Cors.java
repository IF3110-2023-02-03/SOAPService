package org.example.middlewares;

import com.sun.net.httpserver.Filter;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

public class Cors extends Filter {
    @Override
    public void doFilter(HttpExchange exchange, Chain chain) throws IOException {
        System.out.println(exchange);
        exchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
        exchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        exchange.getResponseHeaders().add("Access-Control-Allow-Headers", "Content-Type, Authorization, Content-Length, X-Requested-With");

        System.out.println(exchange.getRequestMethod());
        if (exchange.getRequestMethod().equalsIgnoreCase("OPTIONS")) {
            System.out.println("sini");
            exchange.sendResponseHeaders(200, -1);
        } else {
            System.out.println("sini2");
            chain.doFilter(exchange);
        }
    }

    @Override
    public String description() {
        return "Adds CORS headers to HTTP responses";
    }
}