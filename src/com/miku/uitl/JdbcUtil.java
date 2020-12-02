package com.miku.uitl;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.sql.*;
import java.util.Iterator;
import java.util.Map;

public class JdbcUtil {

     final String URL = "jdbc:mysql://localhost:3306/sunaxuli";
     final String USERNAME="root";
     final String PASSWORD="333";
     PreparedStatement ps = null;
     Connection con = null;

     //------------------通过全局作用域对象得到Connection-----------------------start
     public Connection getCon(HttpServletRequest request){

         //1.通过请求得到全局作用域对象
         ServletContext application = request.getServletContext();
         //2.从全局作用域对象中得到map
         Map map = (Map) application.getAttribute("key1");
         //3.从map中得到一个处于空闲状态的Connection(拖鞋)
         Iterator it = map.keySet().iterator();
         while (it.hasNext()){
             con = (Connection) it.next();
             boolean flag = (boolean) map.get(con);
             if(flag){
                 map.put(con,false);
                 break;
             }
         }
         return con;
     }

     public PreparedStatement createStatement(String sql, HttpServletRequest request){
         try {
             ps = getCon().prepareStatement(sql);
         } catch (SQLException e) {
             e.printStackTrace();
         }
         return ps;
     }

    public void close(HttpServletRequest request){
        if(ps != null){
            try{
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        ServletContext application = request.getServletContext();
        Map map = (Map) application.getAttribute("key1");
        map.put(con,true);

    }
     //------------------通过全局作用域对象得到Connection-----------------------end



    //将jar包中driver实现类加载到JVM中
    static {
        try{
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Connection getCon(){

        try{
            con = DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return con;
    }

    //封装交通工具创建细节
    public PreparedStatement createStatement(String sql){
        try{
            ps = getCon().prepareStatement(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return ps;
    }

    //ps与con销毁细节 insert，update，delete
    public void close(){
        if(ps != null){
            try{
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        if(con != null){
            try{
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    //select ps,con,rs
    public void close(ResultSet rs){
        if(rs != null){
            try{
                rs.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(con != null){
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if(ps != null){
            try {
                ps.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
