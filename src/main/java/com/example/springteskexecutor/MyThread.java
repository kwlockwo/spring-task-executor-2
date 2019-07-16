package com.example.springteskexecutor;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalTime;
import java.util.concurrent.TimeUnit;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Service
public class MyThread implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(MyThread.class);

    @Autowired
    private DataSource dataSource;

    private int count;

    public void setCount(int count) {
        this.count = count;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 15; i++) {
            try (Connection conn = dataSource.getConnection()) {
                Statement stmt = conn.createStatement();
                ResultSet rs = stmt.executeQuery("select uuid_generate_v4()");

                while (rs.next()) {
                    LOGGER.info("Run: {}:{}, Thread: {}, UUID: {}, Time: {}", count, i, Thread.currentThread().getName(), rs.getString(1), LocalTime.now());
                }

                rs.close();
                stmt.close();
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }

            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                LOGGER.info("Run: {}:{}, Thread: {}, Thread interrupted during sleep, Time: {}", count, i, Thread.currentThread().getName(), LocalTime.now());
            }
        }
    }
}