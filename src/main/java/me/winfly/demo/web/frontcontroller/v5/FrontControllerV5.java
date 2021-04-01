package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v1.MemberFormControllerV1;
import me.winfly.demo.web.frontcontroller.v1.MemberListControllerV1;
import me.winfly.demo.web.frontcontroller.v1.MemberSaveControllerV1;
import me.winfly.demo.web.frontcontroller.v2.MemberFormControllerV2;
import me.winfly.demo.web.frontcontroller.v2.MemberListControllerV2;
import me.winfly.demo.web.frontcontroller.v2.MemberSaveControllerV2;
import me.winfly.demo.web.frontcontroller.v2.MyView;
import me.winfly.demo.web.frontcontroller.v3.MemberFormControllerV3;
import me.winfly.demo.web.frontcontroller.v3.MemberListControllerV3;
import me.winfly.demo.web.frontcontroller.v3.MemberSaveControllerV3;
import me.winfly.demo.web.frontcontroller.v3.ModelView;
import me.winfly.demo.web.frontcontroller.v4.MemberFormControllerV4;
import me.winfly.demo.web.frontcontroller.v4.MemberListControllerV4;
import me.winfly.demo.web.frontcontroller.v4.MemberSaveControllerV4;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "frontControllerV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {

    private List<MyHandlerAdapter> adapters = new ArrayList<>();
    private AdapterMapping adapterMapping;

    public FrontControllerV5() {
        adapterMapping = new AdapterMapping();
        initAdapters();
    }

    private void initAdapters() {
        adapters.add(new ControllerV1Adapter());
        adapters.add(new ControllerV2Adapter());
        adapters.add(new ControllerV3Adapter());
        adapters.add(new ControllerV4Adapter());
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object controller = getController(request);
        if (controller == null) {
            throw new NullPointerException("Controller is not null");
        }

        MyHandlerAdapter adapter = getAdapter(controller);
        ModelView modelView = adapter.handle(request, response, controller);

        if (modelView != null) {
            MyView myview = new MyView(modelView.getViewName());
            myview.render(modelView.getModel(), request, response);
        }
    }

    private Object getController(HttpServletRequest request) {
        return adapterMapping.getController(request.getRequestURI());
    }

    private MyHandlerAdapter getAdapter(Object controller) {
        for (MyHandlerAdapter adapter : adapters) {
            if (adapter.supports(controller)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("요청하신 타입의 어탭터를 찾지 못했습니다. controller = " + controller);
    }
}
