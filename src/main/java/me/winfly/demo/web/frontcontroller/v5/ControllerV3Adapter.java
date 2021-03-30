package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v2.MyView;
import me.winfly.demo.web.frontcontroller.v3.ControllerV3;
import me.winfly.demo.web.frontcontroller.v3.ModelView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV3Adapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof ControllerV3);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV3 controller = (ControllerV3) handler;

        Map<String, String> model = getModel(request);
        return controller.process(model);
    }

    private Map<String, String> getModel(HttpServletRequest request) {
        Map<String, String> model = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(name -> model.put(name, request.getParameter(name)));
        return model;
    }
}
