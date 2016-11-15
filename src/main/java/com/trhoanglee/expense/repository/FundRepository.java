package com.trhoanglee.expense.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.trhoanglee.expense.domain.Fund;

public interface FundRepository extends JpaRepository<Fund, String>{
    
    @Query("select f from Fund f where lower(f.comment) like :keyword% "
            + "order by f.date desc")
    List<Fund> searchFunds(@Param("keyword") String keyword, Pageable pageable);
    
    @Modifying
    @Query("delete from Fund where id in (:ids)")
    void deleteFunds(@Param("ids") String... ids);
}
