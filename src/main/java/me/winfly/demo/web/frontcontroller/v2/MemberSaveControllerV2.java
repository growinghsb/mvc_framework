package me.winfly.demo.web.frontcontroller.v2;

import me.winfly.demo.domain.member.Member;
import me.winfly.demo.domain.member.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberSaveControllerV2 implements ControllerV2 {
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));

        Member member = new Member(name, age);
        MemberRepository repository = MemberRepository.getInstance();
        repository.save(member);

        request.setAttribute("member", member);

        return new MyView("save-result");
    }
}
