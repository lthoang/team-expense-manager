package com.trhoanglee.expense.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
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
		
		//search all
		members = memberService.search("", 0, 10);
		assertThat(members.size(), is(equalTo(5)));
		
		//search by id
		members = memberService.search("001", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getEmail(), is(equalTo("email1@test.com")));

		//search by first name
		members = memberService.search("First2", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getId(), is(equalTo("002")));
		
		//search by middle name
		members = memberService.search("Middle3", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getId(), is(equalTo("003")));
		
		//search by last name
        members = memberService.search("Last4", 0, 10);
        assertThat(members.size(), is(equalTo(1)));
        assertThat(members.get(0).getId(), is(equalTo("004")));

        //search by email
        members = memberService.search("email5@test.com", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		assertThat(members.get(0).getId(), is(equalTo("005")));
	}
	
	@Test
	public void testSaveMember() {
		//create
		Member member = new Member(new Name("newF", "newM", "newL"), "newTest@test.com", "123456789", new Date(0L));
		memberService.saveMember(member);
		
		List<Member> members = memberService.search("newF", 0, 10);
		assertThat(members.size(), is(equalTo(1)));
		member = members.get(0);
		assertThat(member.getName().getFullNameFML(), is(equalTo("newF newM newL")));
		
		//update
		member = memberService.getMember("001");
		assertNotNull(member);
		member.setEmail("newTestEmail@test.com");
		memberService.saveMember(member);
		member = memberService.getMember("001");
		assertThat(member.getEmail(), is(equalTo("newTestEmail@test.com")));
	}
	
	@Test
	public void testDeleteMember() {
		List<Member> members;
		members = memberService.search("", 0, 10);
		assertThat(members.size(), is(equalTo(5)));
		memberService.deleteMembers("001", "002");
		
		members = memberService.search("", 0, 10);
		assertThat(members.size(), is(equalTo(3)));
		members.forEach(member -> {
		    assertThat(member.getId(), is(not("001")));
		    assertThat(member.getId(), is(not("002")));
		});
	}
}
