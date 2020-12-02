package com.miku.listener;

import com.miku.uitl.JdbcUtil;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class OneListener implements ServletContextListener {

    //在Tomcat启动时，预先创建20个Connection，在userDao.add方法执行时
    //将事先创建好的Connection交给add方法
    @Override
    public void contextInitialized(ServletContextEvent sce) {

        JdbcUtil util = new JdbcUtil();
        Map map = new HashMap();
        for(int i = 0; i < 20; i++){
            Connection con = util.getCon();
            System.out.println("在Http服务器启动时，创建Connection "+con);
            map.put(con,true);//true表示当前通道处于空闲状态，false表示通道正在被使用
        }
        //为了在Http服务器运行期间一直都可以使用20个Connection，将connection保存到全局作用域对象中
        ServletContext application = sce.getServletContext();
        application.setAttribute("key1",map);
    }//map被销毁

    //在Http服务器关闭时，将全局作用域对象的20个Connection对象销毁
    @Override
    public void contextDestroyed(ServletContextEvent sce) {

        ServletContext application =  sce.getServletContext();
        Map map = (Map) application.getAttribute("key1");
        Iterator it = map.keySet().iterator();//把map的key放到set集合中，调用迭代器做简单的排序
        while (it.hasNext()){
            Connection con = (Connection) it.next();
            if(con != null){
                System.out.println(con+"被关闭");
            }
        }
    }
}
