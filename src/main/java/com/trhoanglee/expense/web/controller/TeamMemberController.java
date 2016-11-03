package com.trhoanglee.expense.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trhoanglee.expense.domain.TeamMember;
import com.trhoanglee.expense.service.TeamMemberService;
import com.trhoanglee.expense.util.CommonUtils;

@RestController
@RequestMapping(value = "/api/teamMembers")
public class TeamMemberController {

	@Autowired
	private TeamMemberService teamMemberService;
	
	@RequestMapping(value = "/{id}", method = GET)
	public TeamMember getTeamMember(@PathVariable("id") String id) {
	    return teamMemberService.getTeamMember(CommonUtils.parsePathVariableId(id));
	}
	
	@RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public TeamMember createTeamMember(@RequestBody TeamMember teamMember) {
		teamMember.setId(null);
		return teamMemberService.saveTeamMember(teamMember);
	}
	
	@RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public TeamMember updateTeamMember(
			@PathVariable("id") String id, 
			@RequestBody TeamMember teamMember) {
		teamMember.setId(CommonUtils.parsePathVariableId(id));
		return teamMemberService.saveTeamMember(teamMember);
	}
	
	@RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam Long[] ids) {
	    teamMemberService.deleteTeamMembers(ids);
    }
}
