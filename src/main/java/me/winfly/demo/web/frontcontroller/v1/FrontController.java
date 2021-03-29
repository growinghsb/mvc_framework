package me.winfly.demo.web.frontcontroller.v1;

import me.winfly.demo.domain.member.Member;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@WebServlet(name = "frontController", urlPatterns = "/front-controller/v1/*")
public class FrontController extends HttpServlet {

    private Map<String, ControllerV1> controllerV1s = new HashMap<>();

    public FrontController() {
        controllerV1s.put("/front-controller/v1/members/new-form", new MemberFormController());
        controllerV1s.put("/front-controller/v1/members/save", new MemberSaveController());
        controllerV1s.put("/front-controller/v1/members", new MemberListController());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ControllerV1 controller = controllerV1s.get(request.getRequestURI());
        controller.process(request, response);
    }
}
