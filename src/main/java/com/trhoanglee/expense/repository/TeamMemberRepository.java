package com.trhoanglee.expense.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.TeamMember;

public interface TeamMemberRepository extends JpaRepository<TeamMember, String>{

	@Query("delete from TeamMember where id in (:ids)")
	void deleteTeamMembers(@Param("ids") String... ids);
}
