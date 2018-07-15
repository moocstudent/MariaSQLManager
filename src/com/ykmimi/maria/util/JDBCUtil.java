package com.ykmimi.maria.util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

public class JDBCUtil {
    private static Properties ps = new Properties();

    static {
        InputStream is = null;
        try{
            is = JDBCUtil.class.getResourceAsStream("/com/ykmimi/maria/config/jdbc.properties");
            ps.load(is);
        } catch (Exception e){
            e.printStackTrace();
            throw new ExceptionInInitializerError();
        } finally{
            if(is!=null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    /////* Connection
    private static Connection conn;
    public static Connection getConnection() throws ClassNotFoundException, SQLException {
        if(conn==null){
            Class.forName(ps.getProperty("driver"));
            String url = ps.getProperty("url");
            String user = ps.getProperty("user");
            String password = ps.getProperty("password");
            conn = DriverManager.getConnection(url,user,password);
        }
        return conn;
    }

    /////* close关闭调用
    public static void close(ResultSet rs,Statement statement,Connection conn){
        if(rs!=null) {
            try {
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement!=null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (conn!=null){
            try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
