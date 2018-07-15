package com.ykmimi.maria.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SequenceNextVal {

    public static long getSequenceNextVal(String seqNameNext){
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        long seqNum = 0;
        String nextvalSql = "select "+seqNameNext+"  from dual";
        try {
            conn = JDBCUtil.getConnection();
            ps = conn.prepareStatement(nextvalSql);
//            ps.setString(1,seqNameNext);
            rs = ps.executeQuery();
            while(rs.next()){
                seqNum = rs.getLong(1);
                break;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JDBCUtil.close(rs,ps,null);
        }
        return seqNum;
    }
}
