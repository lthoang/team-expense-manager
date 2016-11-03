package com.trhoanglee.expense.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.Team;

public interface TeamRepository extends JpaRepository<Team, Long>{

	@Query("select t from Team t where lower(t.name) like :keyword% "
			+ "or lower(t.description) like :keyword% "
			+ "order by t.name")
	List<Team> searchTeams(@Param("keyword") String keyword, Pageable pageable);

	@Query("delete from Team where id in (:ids)")
	void deleteTeams(@Param("ids") Long... ids);
}
