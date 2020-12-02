package com.miku.controller;

import com.miku.dao.UserDao;
import com.miku.entity.Users;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class UserAdd_Servlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userName,password,sex,email;
        UserDao dao = new UserDao();
        Users user = null;
        int result = 0;
        PrintWriter out = null;

        //1.[调用请求对象]，读取[请求头]参数信息，得到用户的信息
        userName = request.getParameter("userName");
        password = request.getParameter("password");
        sex = request.getParameter("sex");
        email = request.getParameter("email");
        //2.[调用UserDao]，将用户信息填充到Insert命令中并借助JDBC规范发送到服务器中
        user = new Users(null,userName,password,sex,email);
        Date startDate = new Date();
        result = dao.add(user,request);
        Date endtDate = new Date();
        System.out.println("添加消耗时间 = "+(endtDate.getTime()-startDate.getTime()));
        //3.[调用响应对象]将[处理结果]以二进制形式写入到响应体中
        response.setContentType("text/html;charset=utf-8");
        out = response.getWriter();
        if(result == 1){
            out.print("<fount style='color:red;font-size:40'>用户信息注册成功</fount>");
        }else{
            out.print("<fount style='color:red;font-size:40'>用户信息注册失败</fount>");
        }

        //服务器自动处理
        //Tomcat负责销毁[请求对象]和[响应对象]

        //Tomcat负责将http响应协议包推送到发起请求的浏览器上
        //浏览器根据响应头content—type指定编译器对响应体二进制内容编辑
        //浏览器将编辑后的结果在窗口中展示给用户
        //互联网通信结束
    }
}
