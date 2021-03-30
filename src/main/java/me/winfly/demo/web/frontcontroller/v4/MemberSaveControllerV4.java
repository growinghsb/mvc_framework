package me.winfly.demo.web.frontcontroller.v4;

import me.winfly.demo.domain.member.Member;
import me.winfly.demo.domain.member.MemberRepository;
import me.winfly.demo.web.frontcontroller.v3.ControllerV3;
import me.winfly.demo.web.frontcontroller.v3.ModelView;

import java.util.Map;

public class MemberSaveControllerV4 implements ControllerV4 {
    @Override
    public String process(Map<String, String> paramModel, Map<String, Object> model) {
        String userName = paramModel.get("username");
        int age = Integer.parseInt(paramModel.get("age"));

        Member member = new Member(userName, age);
        MemberRepository repository = MemberRepository.getInstance();
        repository.save(member);

        model.put("member", member);

        return "save-result";
    }
}
