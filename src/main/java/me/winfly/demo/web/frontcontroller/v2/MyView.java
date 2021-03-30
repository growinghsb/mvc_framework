package me.winfly.demo.web.frontcontroller.v2;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MyView {
    private String viewPath;

    public MyView(String viewPath) {
        this.viewPath = viewPath;
    }

    public void render(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String jspViewPath = "/WEB-INF/views/" + viewPath + ".jsp";
        RequestDispatcher dispatcher = request.getRequestDispatcher(jspViewPath);
        dispatcher.forward(request, response);
    }
}
