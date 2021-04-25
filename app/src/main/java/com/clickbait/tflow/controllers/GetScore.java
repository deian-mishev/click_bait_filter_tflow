package com.clickbait.tflow.controllers;

import java.io.OutputStream;
import java.sql.Connection;

import com.clickbait.tflow.dataSource.DBCPDataSource;
import com.sun.net.httpserver.HttpExchange;

public class GetScore extends Thread {
    HttpExchange exchange;
    DBCPDataSource connection;

    public GetScore() {
        super();
    }

    public GetScore(HttpExchange ex, DBCPDataSource con) {
        exchange = ex;
        connection = con;
    }

    public void run() {
        try (Connection con = connection.getConnection()) {
            String caller = exchange.getLocalAddress().getHostName();
            System.out.println("Ping From - " + caller);
            String response = "Echo " + caller;
            exchange.sendResponseHeaders(200, response.length());
            OutputStream os = exchange.getResponseBody();
            os.write(response.getBytes());
            os.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}