package me.winfly.demo.web.frontcontroller.v4;

import me.winfly.demo.web.frontcontroller.v3.ControllerV3;
import me.winfly.demo.web.frontcontroller.v3.ModelView;
import org.apache.taglibs.standard.lang.jstl.StringLiteral;

import java.util.Map;

public class MemberFormControllerV4 implements ControllerV4 {
    @Override
    public String process(Map<String, String> paramModel, Map<String, Object> model) {
        return "new-form";
    }
}
