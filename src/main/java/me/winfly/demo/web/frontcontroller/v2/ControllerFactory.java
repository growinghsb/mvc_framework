package me.winfly.demo.web.frontcontroller.v2;

import java.util.HashMap;
import java.util.Map;

public class ControllerFactory {
    private static final Map<String, ControllerV2> controllers = new HashMap<>();

    private ControllerFactory() {
        controllers.put("/front-controller/v2/members/new-form", new MemberFormControllerV2());
        controllers.put("/front-controller/v2/members/save", new MemberSaveControllerV2());
        controllers.put("/front-controller/v2/members", new MemberListControllerV2());
    }

    public static ControllerV2 getController(String requestUrl) {
        new ControllerFactory();
        return controllers.get(requestUrl);
    }
}
