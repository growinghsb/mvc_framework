package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v2.MyView;
import me.winfly.demo.web.frontcontroller.v3.ModelView;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "frontControllerV5", urlPatterns = "/front-controller/v5/*")
public class FrontControllerV5 extends HttpServlet {

    private AdapterMapping adapterMapping;
    private HandlerAdapter adapters;


    public FrontControllerV5() {
        adapterMapping = new AdapterMapping();
        adapters = new HandlerAdapter();
    }

    @Override
    protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Object controller = adapterMapping.getController(request.getRequestURI());
        if (controller == null) {
            throw new NullPointerException("Controller is not null");
        }

        MyHandlerAdapter adapter = adapters.getAdapter(controller);
        ModelView modelView = adapter.handle(request, response, controller);

        if (modelView != null) {
            MyView myview = new MyView(modelView.getViewName());
            myview.render(modelView.getModel(), request, response);
        }
    }
}
