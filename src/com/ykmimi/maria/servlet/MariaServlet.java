package com.ykmimi.maria.servlet;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ykmimi.maria.manager.MariaSQLManager;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;

@WebServlet(name = "MariaServlet")
public class MariaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        //TODO接收showdb.jsp传入的sql语句
        response.setContentType("text/html;charset=utf-8");
        response.setCharacterEncoding("utf-8");
        request.setCharacterEncoding("utf-8");

        /////* get the SQL statement to translate
        String sql = request.getParameter("web_sql");
//        int sqlType = MariaSQLManager.sql_Judger(sql);

        try {
            String showResult = MariaSQLManager.sql_Handler(sql);
            if(showResult.length()!=0){
                PrintWriter pw = response.getWriter();/////pw是response的
                pw.write(showResult);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
