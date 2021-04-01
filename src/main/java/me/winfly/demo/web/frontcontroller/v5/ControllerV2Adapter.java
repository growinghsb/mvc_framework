package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v2.ControllerV2;
import me.winfly.demo.web.frontcontroller.v2.MyView;
import me.winfly.demo.web.frontcontroller.v3.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class ControllerV2Adapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof ControllerV2);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV2 controller = (ControllerV2) handler;
        MyView myView = controller.process(request, response);

        return new ModelView(myView.getViewPath());
    }
}
