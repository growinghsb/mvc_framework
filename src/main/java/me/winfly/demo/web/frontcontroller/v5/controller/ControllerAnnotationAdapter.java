package me.winfly.demo.web.frontcontroller.v5.controller;

import me.winfly.demo.web.frontcontroller.v3.ModelView;
import me.winfly.demo.web.frontcontroller.v5.MyHandlerAdapter;
import org.springframework.ui.Model;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class ControllerAnnotationAdapter implements MyHandlerAdapter {
    @Override
    public boolean supports(Object controller) {
        return (controller instanceof MemberController);

    }

    @Override
    public ModelView handle(HttpServletRequest request, HttpServletResponse response, Object handler) throws ServletException, IOException {
        String relativePath = requestUrl(request.getRequestURI());
        Method[] methods = MemberController.class.getDeclaredMethods();

        for (Method method : methods) {
            HttpMethodMapping hmm = method.getAnnotation(HttpMethodMapping.class);
            if (hmm.url().equals(relativePath)) {
                return getModelView(method.getParameterCount(), (MemberController) handler, request);
            }
        }
        throw new IllegalArgumentException("잘못된 url 요청 입니다. url = " + relativePath);
    }

    private String requestUrl(String url) {
        String[] token = url.split("/");
        for (int i = 0; i < token.length; i++) {
            if (token[i].equalsIgnoreCase("v5")) {
                return token[token.length - 1];
            }
        }
        throw new IllegalArgumentException("잘못된 url 요청 입니다. url = " + url);
    }

    private Map<String, String> getParameter(HttpServletRequest request) {
        Map<String, String> parameter = new HashMap<>();
        request.getParameterNames()
                .asIterator()
                .forEachRemaining(name -> parameter.put(name, request.getParameter(name)));

        return parameter;
    }

    private ModelView getModelView(int methodParameterCount, MemberController controller, HttpServletRequest request) {
        ModelView mv = null;
        Map<String, Object> model = new HashMap<>();
        switch (methodParameterCount) {
            case 0:
                mv = new ModelView(controller.memberNewForm());
                System.out.println(mv.getViewName());
                break;
            case 1:
                mv = new ModelView(controller.memberList(model));
                mv.setModel(model);
                break;
            case 2:
                mv = new ModelView(controller.memberSave(getParameter(request), model));
                mv.setModel(model);
                break;
        }
        return mv;
    }
}
