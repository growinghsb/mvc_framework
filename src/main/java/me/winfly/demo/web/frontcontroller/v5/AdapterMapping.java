package me.winfly.demo.web.frontcontroller.v5;

import me.winfly.demo.web.frontcontroller.v1.MemberFormControllerV1;
import me.winfly.demo.web.frontcontroller.v1.MemberListControllerV1;
import me.winfly.demo.web.frontcontroller.v1.MemberSaveControllerV1;
import me.winfly.demo.web.frontcontroller.v2.MemberFormControllerV2;
import me.winfly.demo.web.frontcontroller.v2.MemberListControllerV2;
import me.winfly.demo.web.frontcontroller.v2.MemberSaveControllerV2;
import me.winfly.demo.web.frontcontroller.v3.MemberFormControllerV3;
import me.winfly.demo.web.frontcontroller.v3.MemberListControllerV3;
import me.winfly.demo.web.frontcontroller.v3.MemberSaveControllerV3;
import me.winfly.demo.web.frontcontroller.v4.MemberFormControllerV4;
import me.winfly.demo.web.frontcontroller.v4.MemberListControllerV4;
import me.winfly.demo.web.frontcontroller.v4.MemberSaveControllerV4;
import me.winfly.demo.web.frontcontroller.v5.controller.MemberController;

import java.util.HashMap;
import java.util.Map;

public class AdapterMapping {
    private Map<String, Object> adapterMapping = new HashMap<>();

    public AdapterMapping() {
        adapterMapping.put("/front-controller/v5/v1/members/new-form", new MemberFormControllerV1());
        adapterMapping.put("/front-controller/v5/v1/members/save", new MemberSaveControllerV1());
        adapterMapping.put("/front-controller/v5/v1/members", new MemberListControllerV1());

        adapterMapping.put("/front-controller/v5/v2/members/new-form", new MemberFormControllerV2());
        adapterMapping.put("/front-controller/v5/v2/members/save", new MemberSaveControllerV2());
        adapterMapping.put("/front-controller/v5/v2/members", new MemberListControllerV2());

        adapterMapping.put("/front-controller/v5/v3/members/new-form", new MemberFormControllerV3());
        adapterMapping.put("/front-controller/v5/v3/members/save", new MemberSaveControllerV3());
        adapterMapping.put("/front-controller/v5/v3/members", new MemberListControllerV3());

        adapterMapping.put("/front-controller/v5/v4/members/new-form", new MemberFormControllerV4());
        adapterMapping.put("/front-controller/v5/v4/members/save", new MemberSaveControllerV4());
        adapterMapping.put("/front-controller/v5/v4/members", new MemberListControllerV4());

        adapterMapping.put("/front-controller/v5/members/new-form", new MemberController());
        adapterMapping.put("/front-controller/v5/members/save", new MemberController());
        adapterMapping.put("/front-controller/v5/members", new MemberController());
    }

    public Object getController(String requestUrl) {
            return adapterMapping.get(requestUrl);
    }
}
