package com.trhoanglee.expense.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trhoanglee.expense.domain.TeamMember;
import com.trhoanglee.expense.repository.TeamMemberRepository;

@Service
@Transactional(readOnly = true)
public class TeamMemberService {
	private TeamMemberRepository teamMemberRepo;

	public TeamMember getTeamMember(Long id) {
		return teamMemberRepo.findOne(id);
	}

	public TeamMember saveTeamMember(TeamMember teamMember) {
		return teamMemberRepo.save(teamMember);
	}

	public void deleteTeamMembers(Long... ids) {
		teamMemberRepo.deleteTeamMembers(ids);
	}

}
