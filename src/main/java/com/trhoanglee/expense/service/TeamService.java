package com.trhoanglee.expense.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trhoanglee.expense.domain.Team;
import com.trhoanglee.expense.repository.MemberRepository;
import com.trhoanglee.expense.repository.TeamRepository;

@Service
@Transactional(readOnly = true)
public class TeamService {
    private final AtomicInteger idGeneration = new AtomicInteger(1000);
    
	@Autowired
	private TeamRepository teamRepo;
	
	@Autowired
	private MemberRepository memberRepo;
	
    public String idGenerationIncrementAndGet() {
        return String.valueOf(idGeneration.incrementAndGet());
    }
    
	public List<Team> search(String keyword, int page, int pageSize) {
		keyword = (keyword == null) ? "" : keyword.toLowerCase();
		return teamRepo.searchTeams(keyword, new PageRequest(page, pageSize));
	}

	public Team getTeam(String id) {
		return teamRepo.findOne(id);
	}

	@Transactional
	public Team saveTeam(@Valid Team team) {
	    if (team == null) {
	        return null;
	    }
	    if (team.getId() == null) {
	        team.setId(idGenerationIncrementAndGet());
	    }
		return teamRepo.save(team);
	}

	@Transactional
	public void deleteTeams(String... ids) {
		teamRepo.deleteTeams(ids);
	}
	

    public void deleteAllTeams() {
        teamRepo.deleteAllInBatch();
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
	
	/**
	 * teamLine format: id|name|description|created_date|manager_id
	 */
	private Team parseTeam(String teamLine) {
		String[] items = teamLine.split("\\|");
		if (items.length < 5) {
			throw new IllegalArgumentException(String.format("Invalid team-line format: %s", teamLine));
		}

		Team team = new Team();
		team.setId(items[0]);
		team.setName(items[1]);
		team.setDescription(items[2]);
		team.setCreatedDate(new Date(java.sql.Date.valueOf(items[3]).getTime()));
		team.setManager(memberRepo.findOne(items[4]));
		return team;
		
	}

}
