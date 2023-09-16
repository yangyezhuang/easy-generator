package com.easy.generator.utils;

import com.easy.generator.model.Db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DbUtils
 *
 * @author: YZ.YANG
 * @date: 2023-09-16 20:57
 */
public class DBUtils {
    private static Connection connection;

    public static Connection getConnection() {
        return connection;
    }

    public static Connection initDb(Db db) {
        if (connection == null) {
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                connection = DriverManager.getConnection(db.getUrl(), db.getUsername(), db.getPassword());
            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

}
