package me.winfly.demo.web.frontcontroller.v4;

import me.winfly.demo.domain.member.MemberRepository;
import me.winfly.demo.web.frontcontroller.v3.ControllerV3;
import me.winfly.demo.web.frontcontroller.v3.ModelView;

import java.util.Map;

public class MemberListControllerV4 implements ControllerV4 {


    @Override
    public String process(Map<String, String> paramModel, Map<String, Object> model) {
        MemberRepository repository = MemberRepository.getInstance();

        model.put("members", repository.findAll());

        return "members";
    }
}
