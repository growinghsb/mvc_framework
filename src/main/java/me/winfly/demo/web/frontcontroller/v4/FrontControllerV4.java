package me.winfly.demo.web.frontcontroller.v4;

import me.winfly.demo.web.frontcontroller.v2.MyView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerV4", urlPatterns = "/front-controller/v4/*")
public class FrontControllerV4 extends HttpServlet {

    private final Map<String, ControllerV4> controllers = new HashMap<>();

    public FrontControllerV4() {
        controllers.put("/front-controller/v4/members/new-form", new MemberFormControllerV4());
        controllers.put("/front-controller/v4/members/save", new MemberSaveControllerV4());
        controllers.put("/front-controller/v4/members", new MemberListControllerV4());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV4 controller = controllers.get(request.getRequestURI());

        if (controller == null) {
            throw new NullPointerException("controller is null");
        }

        Map<String, String> paramModel = createParameter(request);
        Map<String, Object> model = new HashMap<>();
        MyView myView = new MyView(controller.process(paramModel, model));

        myView.render(model, request, response);
    }

    private Map<String, String> createParameter(HttpServletRequest request) {
        Map<String, String> paramModel = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(key -> paramModel.put(key, request.getParameter(key)));
        return paramModel;
    }
}
