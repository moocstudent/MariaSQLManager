package com.ykmimi.maria.manager;



import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
/**
 * 针对所有表可查方法.
 */
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Scanner;

import com.ykmimi.maria.util.JDBCUtil;

/**
 * 创建时间：2017年12月4日 上午12:37:45 项目名称：网络编程
 *
 * @author ukyozq
 * @version 1.0
 * @since JDK 9.0
 */
public class MariaSQLManager {
    private static Scanner in = new Scanner(System.in);


    /////*
    public static int sql_Judger(String input_SQL) {
        String sql1 = input_SQL;
        sql1.toLowerCase();
        int sql1Len = sql1.length();
        /* 如果输入的sql有分号结尾;则去掉 */
        if (sql1.endsWith(";")) {
            sql1 = sql1.substring(0, sql1Len - 1);
        }
        String[] oneOfSql1Array = sql1.split(" ");
        // 是选择,插入还是更新语句?当判定到为哪种sql后,给予true,并执行对应操作.//
        int SQLType = 0;
        //////////////////////
        if (oneOfSql1Array[0].equals("select")) {
            SQLType = 1;// 为1是select
        } else if (oneOfSql1Array[0].equals("insert")) {
            SQLType = 2;// 为2是insert
        } else if (oneOfSql1Array[0].equals("update")) {
            SQLType = 3;// 为3是update
        } else if (oneOfSql1Array[0].equals("delete")) {
            SQLType = 4;// 为4是delete
        } else if (oneOfSql1Array[0].equals("alter")) {
            SQLType = 5;// 为5是alter
        }
        return SQLType;
    }// sql_judger end


    ////////////////////////////////////////////
    ///// * 使用新的sql_Handler(String sql) */////
    ////////////////////////////////////////////
    public static String sql_Handler(String sql) throws SQLException {

        // * *//
        int judger_Num = sql_Judger(sql);
        // * 对sql进行判定后,应该知道其类型,并能根据其类型,执行不同操作 *//
        // * select 执行for()循环输出所查询的字段 *//
        // * insert 执行事务处理 *//
        // * update 执行事务处理 *//
        // * delete 执行事务处理 *//
        // 将sql语句分离,获取其中的[表名:getTableName1]
        String[] oneOfSql1Array = sql.split(" ");
        String getTableName1 = null;

        for (int i = 0; i < oneOfSql1Array.length; i++) {
            if (oneOfSql1Array[i].equals("from")) {
                getTableName1 = oneOfSql1Array[i + 1];
                break;
            }
        }

        // ResultSet rs = null;
        // ResultSetMetaData rsmd = null;
        // ResultSet rset = null;
        // PreparedStatement ps = null;
        // Connection conn = null;

        // File file = new File(getTableName1 + "_sql_info.txt");
        // System.out.println("您好,您所查询的数据库信息将写入到"+file.getName()+"中.💗");
        // try {
        // file.createNewFile();
        // } catch (IOException e1) {
        // e1.printStackTrace();
        // }
        // // file为根据输入查询的tableName组成的txt文件实例.
        // FileOutputStream fops = null;
        // try {
        // fops = new FileOutputStream(file);
        // } catch (FileNotFoundException e1) {
        // e1.printStackTrace();
        // }
        // PrintStream psm = new PrintStream(fops);
        // 将该方法下面的要System.out.print() println() 输出的内容不再用于控制台输出,直接写入文件.
        // System.setOut(psm);

        /*
         * DriverManager 接口是JDBC的管理层,作用于用户和驱动程序之间, DriverManager
         * 跟踪可用的驱动程序,并在数据库和相应的驱动程序之间建立连接.
         */
        // 建立连接(连接对象内部其实包含了Socket对象,是一个远程的连接,比较耗时!这是Connection对象管理的一个要点!)
        Connection conn = null;
        try {
            conn = JDBCUtil.getConnection();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        // PreparedStatement ps = conn.prepareStatement(sql);
        // 使用PreparedStatement (效率更高,预处理机制.防止SQL注入) //占位符?
        // String sql = "SELECT employee_id FROM " + tableName;

        ///// * 原本使用齐全的sql查询不会受影响 */////
        ///// * 而使用半成品sql,以及数据集合传入的话(其他类中传入),会根据这个传入的ps来执行操作
        String backToShow = "";
        if (judger_Num == 1) {
            // 执行 用结果集获取
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();

            backToShow+="<table border='1'><tr>";
            // 输出列名
            for (int i = 1; i <= columnCount; i++) {
                System.out.print(rsmd.getColumnName(i) + "\t");
                backToShow+="<th>"+rsmd.getColumnName(i) + "</th>";

            }
            System.out.println();
            //
            backToShow+="</tr>";



//			reg.setNameCheckNum(0);// 设置用户名查询不重复0. 接下来如果重复就会被查询到在while中设置了1
//			log.setID_IsPassed(false);// 设置了ID不存在,如果接下来在while中查询到的数据不为空则证明查询到了该ID,并传入true(表示存在)
            while (rs.next()) {
                backToShow+="<tr>";
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsmd.getColumnName(i) + ":\t");
//                    backToShow+=rsmd.getColumnName(i) + "";
                    Object data = rs.getObject(i);
                    System.out.print(data + "\t");

                    backToShow+="<td>"+data + "</td>";
                    ///// * 只针对user_name判定的集合使用 */////

                    // 如果数据不为空,在执行 select * from lib_users where card_id = 输入的ID,
                    // 则查看该数据如果不为空,则传入true,即查询的输入的ID是存在的.

                    // 数据类型,在查看书时先隐藏
                    System.out.print(
                            "(数据类型:" + rsmd.getColumnTypeName(i) + "," + rsmd.getColumnDisplaySize(i) +
                                    "字节)\t");
//                    backToShow+="(数据类型:" + rsmd.getColumnTypeName(i) + "," + rsmd.getColumnDisplaySize(i) +
//                            "字节)$nbsp;$nbsp;$nbsp;$nbsp;";

                }
                System.out.println();
                backToShow+="</tr>";
//                backToShow +="<br>";
            }
            backToShow+="</table>";

            System.out.println("------");
            backToShow+= "------<br>";
            // 取得列数(字段数)
            ResultSet rset = ps.executeQuery("select count(*) totalCount from " + getTableName1);
            int rowCount = 0;
            while (rset.next()) {
                rowCount = rset.getInt("totalCount");

            }
            System.out.println(rsmd.getCatalogName(rowCount) + "数据库中的" + rsmd.getTableName(rowCount) + "表,有" + rowCount
                    + "行" + columnCount + "列.");
            backToShow+=rsmd.getCatalogName(rowCount) + "数据库中的" + rsmd.getTableName(rowCount) + "表,有" + rowCount
                    + "行" + columnCount + "列.<br>";
            System.out.println("------");
            backToShow+="------<br>";
            System.out.println("执行了select语句.");
            backToShow+="执行了select语句.<br>";
            /////* 关流未写


            // 执行完后继续执行MariaSQLManager
            // sql_Handler();
        } else if (judger_Num == 2) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
            /////* 关流未写
            // 执行完后继续执行MariaSQLManager
            // sql_Handler();
            System.out.println("执行了insert语句.");
        } else if (judger_Num == 3) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.executeUpdate();


            /////* 关流未写

            System.out.println("执行了update语句.");
            // 执行完后继续执行MariaSQLManager
            // sql_Handler();
        } else if (judger_Num == 4) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
//			MariaTools.close(null, null, ps, conn);
            // 执行完后继续执行MariaSQLManager
            // sql_Handler();
            System.out.println("执行了delete语句.");
        } else if (judger_Num == 5) {
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.execute();
//			MariaTools.close(null, null, ps, conn);

        }
        return backToShow;

    }// sql_Handle end
    /////


    public static void main(String[] args) {

//		System.out.println("输入您想查询的表的名称: XE Oracle <<<<");
//
//		String tableName = in.next();


        /////*接受输入的SQL查询语句,将其进行判定并获取表名留用到该行👇*/////
//ResultSet rset = ps.executeQuery("select count(*) totalCount from "+getTableName1);//

///////////////////////////////////////////////////////////////////////////////////////
        System.out.println("输入您的查询语句:只针对Oracle-XE <<<");

        String sql1 = in.nextLine();
        sql1.trim();
        sql1.toLowerCase();
        int sql1Len = sql1.length();
        /*如果输入的sql有分号结尾;则去掉*/
        if (sql1.endsWith(";")) {
            sql1 = sql1.substring(0, sql1Len - 1);
        }
        String[] oneOfSql1Array = sql1.split(" ");
        String getTableName1 = null;

        for (int i = 0; i < oneOfSql1Array.length; i++) {
            if (oneOfSql1Array[i].equals("from")) {
                getTableName1 = oneOfSql1Array[i + 1];
                break;
            }
        }

//////////////////////////////////////////////////////////////////////////////////////


        /////*以下方法用于将原本输出到控制台的内容写入到了文件里*/////

////////////////////////////////////////////////////////////////////////////////
        File file = new File(getTableName1 + "_sql_info.txt");
        System.out.println("您好,您所查询的数据库信息将写入到" + file.getName() + "中.");
        try {
            file.createNewFile();
        } catch (IOException e1) {
            e1.printStackTrace();
        }
        // file为根据输入查询的tableName组成的txt文件实例.
        FileOutputStream fops = null;
        try {
            fops = new FileOutputStream(file);
        } catch (FileNotFoundException e1) {
            e1.printStackTrace();
        }
        PrintStream psm = new PrintStream(fops);
        // 将该方法下面的要System.out.print() println() 输出的内容不再用于控制台输出,直接写入文件.
        System.setOut(psm);

////////////////////////////////////////////////////////////////////////////////


        try {
            //注册JDBC驱动. {Oracle的OracleDriver}
            Class.forName("oracle.jdbc.driver.OracleDriver");

            // 测试连接时间
            long start = System.currentTimeMillis();
            /*
             * DriverManager 接口是JDBC的管理层,作用于用户和驱动程序之间, DriverManager
             * 跟踪可用的驱动程序,并在数据库和相应的驱动程序之间建立连接.
             */
            // 建立连接(连接对象内部其实包含了Socket对象,是一个远程的连接,比较耗时!这是Connection对象管理的一个要点!)
            Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@127.0.0.1:1521:XE", "hr", "system");
            // 测试连接时间
            long end = System.currentTimeMillis();
            // System.out.println(conn);
            long useTime = end - start;
            System.out.println("与数据库建立连接成功,耗时:" + useTime + "毫秒.");
            System.out.println("------");
            // 创建一个会话

            // 使用PreparedStatement (效率更高,预处理机制.防止SQL注入) //占位符?
//		String sql = "SELECT employee_id FROM " + tableName;
            String sql = sql1;
            PreparedStatement ps = conn.prepareStatement(sql);

            /////
            long startQuery = System.currentTimeMillis();
            // 执行 用结果集获取
            ResultSet rs = ps.executeQuery(sql);
            ResultSetMetaData rsmd = rs.getMetaData();
            int columnCount = rsmd.getColumnCount();
            while (rs.next()) {
                for (int i = 1; i <= columnCount; i++) {
                    System.out.print(rsmd.getColumnName(i) + ":\t");
                    System.out.print(rs.getObject(i) + "\t");
                    System.out.print("(数据类型:" + rsmd.getColumnTypeName(i) + "," + rsmd.getColumnDisplaySize(i) + "字节)\t");
                }
                System.out.println();
            }
            long endQuery = System.currentTimeMillis();
            long useTimeQuery = endQuery - startQuery;
            System.out.println("查询成功!哈嘿,耗时:" + useTimeQuery + "毫秒.");

            System.out.println("------");
            // 取得列数(字段数)
            ResultSet rset = ps.executeQuery("select count(*) totalCount from " + getTableName1);
            int rowCount = 0;
            while (rset.next()) {
                rowCount = rset.getInt("totalCount");

            }
            System.out.println(rsmd.getCatalogName(rowCount) + "数据库中的" + rsmd.getTableName(rowCount) + "表,有" + rowCount
                    + "行" + columnCount + "列.");

            System.out.println("------");

            // 所有连接在获取后必须关闭. 后开的先关
            // 关闭顺序:ResultSet-->Statement(PreparedStatement)-->Connection

            if (rs != null) {
                rs.close();
            }
            if (rsmd != null) {
                rset.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

}
