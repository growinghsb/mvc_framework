package me.winfly.demo.web.frontcontroller.v2;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ControllerV2 {
    MyView process(HttpServletRequest request, HttpServletResponse response);
}
