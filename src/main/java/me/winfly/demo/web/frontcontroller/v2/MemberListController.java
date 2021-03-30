package me.winfly.demo.web.frontcontroller.v2;

import me.winfly.demo.domain.member.MemberRepository;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class MemberListController implements ControllerV2{
    @Override
    public MyView process(HttpServletRequest request, HttpServletResponse response) {
        MemberRepository repository = MemberRepository.getInstance();

        request.setAttribute("members", repository.findAll());

        return new MyView("members");
    }
}
