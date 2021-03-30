package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v3.ModelView;
import me.winfly.demo.web.frontcontroller.v4.ControllerV4;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ControllerV4Adapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof ControllerV4);
    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        ControllerV4 controller = (ControllerV4) handler;
        Map<String, Object> model = new HashMap<>();

        String viewName = controller.process(getParameterModel(request), model);
        ModelView modelView = new ModelView(viewName);
        modelView.setModel(model);

        return modelView;
    }

    private Map<String, String> getParameterModel(HttpServletRequest request) {
        Map<String, String> paramModel = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(name -> paramModel.put(name, request.getParameter(name)));

        return paramModel;
    }
}
