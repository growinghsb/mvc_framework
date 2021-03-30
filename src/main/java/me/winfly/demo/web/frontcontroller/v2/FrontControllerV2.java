package me.winfly.demo.web.frontcontroller.v2;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "frontControllerV2", urlPatterns = "/front-controller/v2/*")
public class FrontControllerV2 extends HttpServlet {
    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV2 controller = ControllerFactory.getController(request.getRequestURI());

        if (controller == null) {
            throw new NullPointerException("Controller is null");
        }

        MyView myView = controller.process(request, response);
        myView.render(request, response);
    }
}
