package com.util;




import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

public class DBUtilFilter {
        String Connstr = "";
        public Connection Conn = null;
        Statement stmt = null;
        public DBUtilFilter(){
                try{
                        init();
                }catch(Exception e){
                        System.out.print("DBUtil:" + e.getMessage());
                }
        }
    
        public void init() throws Exception {
                try{
                	Class.forName("com.mysql.jdbc.Driver").newInstance(); 
        			String url=com.util.PropertyUtil.getvalue("dburlfilter");
        			Conn = DriverManager.getConnection(url);
                    Conn.setAutoCommit(true);
                    stmt = Conn.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_UPDATABLE);
                }catch(Exception e){
                    System.out.print("DBUtil Init:" + e.getMessage());
                }
        }
   
        public ResultSet Execute(String sql) throws Exception{
                ResultSet rsC = null;
                try{
                        rsC = stmt.executeQuery(sql);
                }catch(Exception e){
                        System.out.println("DBUtil->Execute:" + e.getMessage());
                }
                return rsC;
        }
        //执行SQL语句返回影响行数
        public int Execute(String sql,int iR) throws Exception{
                iR = stmt.executeUpdate(sql);
                return iR;
        }
        //执行插入
        public int Insert(String sql) throws Exception{
                int intTemp = 0 ;
                intTemp = stmt.executeUpdate(sql);
                return intTemp;
        }
        //执行更新
        public int Update(String sql) throws Exception{
                int intTemp = 0 ;
                intTemp = stmt.executeUpdate(sql);
                return intTemp;
        }
        //�??��
        public void destroy() {
                try{
                        stmt.close();
                        Conn.close();
                }catch(Exception e){
                        System.out.print("DBUtil destroy:" + e.getMessage());
                }
        }
      
}

