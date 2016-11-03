package com.trhoanglee.expense.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.assertNotNull;
import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.trhoanglee.expense.Application;
import com.trhoanglee.expense.domain.Member;
import com.trhoanglee.expense.domain.Name;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = Application.class)
public class MemberServiceTest {
	@Autowired
	private MemberService memberService;
	
	@Before
	public void startUp() throws IOException {
		memberService.loadMembersFromFile(getClass().getResource("/members-test.txt").getFile());
	}
	
	@After
	public void tearDown() {
		memberService.deleteAllMembers();
	}
	
	@Test
	public void testSearchMembers() {
		List<Member> members;
		
		members = memberService.search("", 0, 10);
		assertThat(members.size(), is(equalTo(5)));
		
		members = memberService.search("First1", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getId(), is(equalTo(1L)));

		members = memberService.search("Middle2", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getId(), is(equalTo(2L)));
		
		members = memberService.search("Last3", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getId(), is(equalTo(3L)));
		
		members = memberService.search("email4", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getId(), is(equalTo(4L)));
	}
	
	@Test
	public void testSaveMember() {
		//create
		Member member = new Member(new Name("newF", "newM", "newL"), "newTest@test.com", "123456789", new Date(0L));
		memberService.saveMember(member);
		
		List<Member> members = memberService.search("newF", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		member = members.get(0);
		assertNotNull(member);
		assertThat(member.getName().getFullNameFML(), is(equalTo("newF newM newL")));
		
		//update
		member.setEmail("newTestEmail@test.com");
		memberService.saveMember(member);
		members = memberService.search("newF", 0, 10);
		member = members.get(0);
		assertNotNull(member);
		assertThat(member.getEmail(), is(equalTo("newTestEmail@test.com")));
	}
	
	@Test
	public void testDeleteMember() {
		List<Member> members;
		members = memberService.search("", 0, 10);
		assertThat(members.size(), is(equalTo(5)));
		memberService.deleteMembers(1L, 2L);
	}
}
