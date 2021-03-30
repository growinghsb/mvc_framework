package me.winfly.demo.web.frontcontroller.v3;

import me.winfly.demo.web.frontcontroller.v2.MyView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontControllerV3", urlPatterns = "/front-controller/v3/*")
public class FrontControllerV3 extends HttpServlet {

    private final Map<String, ControllerV3> controllers = new HashMap<>();

    public FrontControllerV3() {
        controllers.put("/front-controller/v3/members/new-form", new MemberFormControllerV3());
        controllers.put("/front-controller/v3/members/save", new MemberSaveControllerV3());
        controllers.put("/front-controller/v3/members", new MemberListControllerV3());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV3 controller = controllers.get(request.getRequestURI());

        if (controller == null) {
            throw new NullPointerException("controller is null");
        }

        Map<String, String> paramModel = createParameter(request);
        ModelView mv = controller.process(paramModel);

        MyView myView = new MyView(mv.getViewName());
        myView.render(mv.getModel(), request, response);
    }

    private Map<String, String> createParameter(HttpServletRequest request) {
        Map<String, String> paramModel = new HashMap<>();
        request.getParameterNames().asIterator().forEachRemaining(key -> paramModel.put(key, request.getParameter(key)));
        return paramModel;
    }
}
