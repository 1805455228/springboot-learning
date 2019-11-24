package com.hins.sp02servlet.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author qixuan.chen
 * @date 2019-06-27 21:35
 */
@WebServlet(name = "firstServlet",urlPatterns={"/admin/ser"})
public class FirstServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(" Post First Servlet........");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println(" Get First Servlet........");
    }
}
