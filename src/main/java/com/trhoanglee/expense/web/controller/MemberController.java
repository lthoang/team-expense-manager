package com.trhoanglee.expense.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trhoanglee.expense.domain.Member;
import com.trhoanglee.expense.service.MemberService;
import com.trhoanglee.expense.web.dto.MemberInfo;

@RestController
@RequestMapping(value = "/api/members")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @RequestMapping(method = GET)
    public List<MemberInfo> searchMembers(@RequestParam(defaultValue = "") String keyword,
            @RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int pageSize) {
        List<Member> members = memberService.search(keyword, page, pageSize);
        return convertToDto(members);
    }

    @RequestMapping(value = "/{id}", method = GET)
    public MemberInfo getMember(@PathVariable("id") String id) {
        Member member = memberService.getMember(id);
        return convertToDto(member);
    }

    @RequestMapping(method = POST)
    @ResponseStatus(HttpStatus.CREATED)
    public MemberInfo createMember(@RequestBody MemberInfo member) {
        member.setId(null);
        Member memberEntity = convertToEntity(member);
        memberEntity = memberService.saveMember(memberEntity);
        return convertToDto(memberEntity);
    }

    @RequestMapping(value = "/{id}", method = PUT)
    @ResponseStatus(HttpStatus.ACCEPTED)
    public MemberInfo updateMember(@PathVariable("id") String id, @RequestBody MemberInfo member) {
        member.setId(id);
        Member memberEntity = convertToEntity(member);
        memberEntity = memberService.saveMember(memberEntity);
        return convertToDto(memberEntity);
    }

    @RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMembers(@RequestParam String[] ids) {
        memberService.deleteMembers(ids);
    }

    private List<MemberInfo> convertToDto(List<Member> members) {
        List<MemberInfo> response = new ArrayList<>();
        members.forEach(member -> {
            response.add(convertToDto(member));
        });
        return response;
    }

    private MemberInfo convertToDto(Member member) {
        MemberInfo response = new MemberInfo();
        BeanUtils.copyProperties(member, response);
        return response;
    }

    private Member convertToEntity(MemberInfo member) {
        Member memberEntity = new Member();
        BeanUtils.copyProperties(member, memberEntity);
        return memberEntity;
    }
}
