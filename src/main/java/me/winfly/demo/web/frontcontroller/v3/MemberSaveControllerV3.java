package me.winfly.demo.web.frontcontroller.v3;

import me.winfly.demo.domain.member.Member;
import me.winfly.demo.domain.member.MemberRepository;

import java.util.Map;

public class MemberSaveControllerV3 implements ControllerV3 {
    @Override
    public ModelView process(Map<String, String> paramModel) {
        String userName = paramModel.get("username");
        int age = Integer.parseInt(paramModel.get("age"));

        Member member = new Member(userName, age);
        MemberRepository repository = MemberRepository.getInstance();
        repository.save(member);

        ModelView mv = new ModelView("save-result");
        mv.getModel().put("member", member);

        return mv;
    }
}
