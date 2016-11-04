package com.trhoanglee.expense.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.List;

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

@RestController
@RequestMapping(value = "/api/members")
public class MemberController {

	@Autowired
	private MemberService memberService;

	@RequestMapping(method = GET)
	public List<Member> searchMembers(
			@RequestParam(defaultValue="") String keyword, 
			@RequestParam(defaultValue="0") int page, 
			@RequestParam(defaultValue="10") int pageSize) {
		return memberService.search(keyword, page, pageSize);
	}
	
	@RequestMapping(value = "/{id}", method = GET)
	public Member getMember(@PathVariable("id") String id) {
	    return memberService.getMember(id);
	}
	
	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public Member createMember(@RequestBody Member member) {
		member.setId(null);
		return memberService.saveMember(member);
	}
	
	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public Member updateMember(
			@PathVariable("id") String id, 
			@RequestBody Member member) {
		member.setId(id);
		return memberService.saveMember(member);
	}
	
	@RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam String[] ids) {
	    memberService.deleteMembers(ids);
    }
}
