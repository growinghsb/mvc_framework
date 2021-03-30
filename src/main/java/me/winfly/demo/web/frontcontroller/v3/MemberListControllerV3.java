package me.winfly.demo.web.frontcontroller.v3;

import me.winfly.demo.domain.member.MemberRepository;

import java.util.Map;

public class MemberListControllerV3 implements ControllerV3 {


    @Override
    public ModelView process(Map<String, String> paramModel) {
        MemberRepository repository = MemberRepository.getInstance();

        ModelView mv = new ModelView("members");
        mv.getModel().put("members", repository.findAll());

        return mv;
    }
}
