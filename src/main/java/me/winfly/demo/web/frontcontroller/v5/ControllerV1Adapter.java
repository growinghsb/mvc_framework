package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v1.ControllerV1;
import me.winfly.demo.web.frontcontroller.v3.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerV1Adapter implements MyHandlerAdapter{
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof ControllerV1);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {

        ControllerV1 controller = (ControllerV1) handler;
        controller.process(request, response);

        return null;
    }
}
