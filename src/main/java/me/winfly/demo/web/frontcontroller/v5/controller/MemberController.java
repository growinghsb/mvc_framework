package me.winfly.demo.web.frontcontroller.v5.controller;

import me.winfly.demo.domain.member.Member;
import me.winfly.demo.domain.member.MemberRepository;

import java.util.Map;

@MyController(url = "/front-controller/v5/")
public class MemberController {

    @HttpMethodMapping(url = "new-form", httpMethod = "GET")
    public String memberNewForm() {
        return "new-form";
    }

    @HttpMethodMapping(url = "save", httpMethod = "POST")
    public String memberSave(Map<String, String> parameter, Map<String, Object> model) {
        Member member = new Member();

        member.setUsername(parameter.get("username"));
        member.setAge(Integer.parseInt(parameter.get("age")));

        MemberRepository repository = MemberRepository.getInstance();
        repository.save(member);

        model.put("member", member);

        return "save-result";
    }

    @HttpMethodMapping(url = "members", httpMethod = "GET")
    public String memberList(Map<String, Object> model) {
        MemberRepository repository = MemberRepository.getInstance();

        model.put("members", repository.findAll());

        return "members";
    }
}
