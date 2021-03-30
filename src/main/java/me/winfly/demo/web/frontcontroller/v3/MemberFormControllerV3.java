package me.winfly.demo.web.frontcontroller.v3;

import java.util.Map;

public class MemberFormControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramModel) {
        return new ModelView("new-form");
    }
}
