package com.club.test.controller;

import com.club.test.exception.MemberWithSuchEmailExistsException;
import com.club.test.model.Member;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Controller
public class MemberController {
    private Integer id = 0;
    private static List<Member> members = new ArrayList<>();

    @GetMapping("/main")
    public String getAllMembers(Model model) {
        Iterable<Member> members = this.members;
        model.addAttribute("members", members);
        return "main";
    }

    @PostMapping("/main")
    public String addMember(@RequestParam String name, @RequestParam String email, Model model) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        String finishingDateAndTime = LocalDateTime.now().format(formatter);
        Member member = new Member(++id, name, email, finishingDateAndTime);
        for (Member presentedMember : members) {
            if (member.getEmail().equals(presentedMember.getEmail())) {
                id--;
                System.out.println("Member with such email is already presented");
                throw new MemberWithSuchEmailExistsException();
            }
        }
        members.add(member);
        System.out.println("Added new member: " + member);
        model.addAttribute("members", members);
        return "main";
    }
}

