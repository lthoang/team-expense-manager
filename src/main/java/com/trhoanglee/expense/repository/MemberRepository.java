package com.trhoanglee.expense.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
    
    @Query("select m from Member m where lower(m.email) like :keyword% "
            + "or lower(m.mobile) like :keyword% "
            + "or lower(m.name.firstName) like :keyword% "
            + "or lower(m.name.fullNameFML) like :keyword% "
            + "order by m.name.firstName")
    List<Member> searchMembers(@Param("keyword") String keyword, Pageable pageable);

    @Query("delete from Member where id in (:ids)")
    void deleteMembers(@Param("ids") String[] ids);

}
