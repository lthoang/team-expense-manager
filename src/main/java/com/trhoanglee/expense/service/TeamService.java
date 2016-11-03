package com.trhoanglee.expense.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trhoanglee.expense.domain.Member;
import com.trhoanglee.expense.domain.Team;
import com.trhoanglee.expense.domain.TeamMember;
import com.trhoanglee.expense.repository.MemberRepository;
import com.trhoanglee.expense.repository.TeamRepository;

@Service
@Transactional(readOnly = true)
public class TeamService {

	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
	public List<Team> search(String keyword, int page, int pageSize) {
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		return teamRepo.searchTeams(keyword, new PageRequest(page, pageSize));
	}

	public Team getTeam(Long id) {
		return teamRepo.getOne(id);
	}

	@Transactional
	public Team saveTeam(@Valid Team team) {
		return teamRepo.save(team);
	}

	@Transactional
	public void deleteTeams(Long... ids) {
		teamRepo.deleteTeams(ids);
	}

	@Transactional
	public long loadTeamsFromFile(String filePath) throws IOException {
		try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
			return reader.lines()
					.map(this::parseTeam)
					.map(this::saveTeam)
					.count();
		}
	}
	
	private Team parseTeam(String teamLine) {
		String[] items = teamLine.split("\\|");
		if (items.length < 4) {
			throw new IllegalArgumentException(String.format("Invalid team-line format: %s", teamLine));
		}
		
		Team team = new Team();
		try {
			team.setId(Long.parseLong(items[0]));
			team.setName(items[1]);
			team.setDescription(items[2]);
			team.setManager(memberRepo.getOne(Long.parseLong(items[3])));
			if (items.length > 4) {
				String[] memberIds = items[4].split(",");
				for (String memberId : memberIds) {
					Member member = memberRepo.getOne(Long.parseLong(memberId));
					team.getTeamMembers().add(new TeamMember(team, member));
				}
			}
		} catch (Exception ex) {
			throw new IllegalArgumentException(String.format("Invalid team-line format: %s", teamLine, ex));
		}
		return team;
		
	}
}
