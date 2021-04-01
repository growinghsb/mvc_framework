package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v5.controller.ControllerAnnotationAdapter;

import java.util.ArrayList;

public class HandlerAdapter {
    private ArrayList<MyHandlerAdapter> adapters = new ArrayList<>();

    public HandlerAdapter() {
        adapters.add(new ControllerV2Adapter());
        adapters.add(new ControllerV1Adapter());
        adapters.add(new ControllerV3Adapter());
        adapters.add(new ControllerV4Adapter());
        adapters.add(new ControllerAnnotationAdapter());
    }
    public MyHandlerAdapter getAdapter(Object controller) {
        for (MyHandlerAdapter adapter : adapters) {
            if (adapter.supports(controller)) {
                return adapter;
            }
        }
        throw new IllegalArgumentException("요청하신 타입의 어탭터를 찾지 못했습니다. controller = " + controller);
    }
}
