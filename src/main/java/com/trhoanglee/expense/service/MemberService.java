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

import com.trhoanglee.expense.domain.Member;
import com.trhoanglee.expense.domain.Name;
import com.trhoanglee.expense.repository.MemberRepository;

/**
 * @author trhoanglee
 */
@Service
@Transactional(readOnly = true)
public class MemberService {
    private final AtomicInteger idGeneration = new AtomicInteger(1000);
    
    @Autowired
    private MemberRepository memberRepo;
    
    public String idGenerationIncrementAndGet() {
        return String.valueOf(idGeneration.incrementAndGet());
    }
    
    @Transactional
    public Member saveMember(@Valid Member member) {
    	if (member == null) {
    		return null;
    	}
    	if (member.getId() == null) {
    	    member.setId(this.idGenerationIncrementAndGet());
    	}
        return memberRepo.save(member);
    }

    @Transactional
    public void deleteAllMembers() {
        memberRepo.deleteAllInBatch();
    }

    public Member getMember(String id) {
        return memberRepo.findOne(id);
    }

    public List<Member> search(String keyword, int page, int pageSize) {
        keyword = (keyword == null) ? "" : keyword.toLowerCase();
        return memberRepo.searchMembers(keyword, new PageRequest(page, pageSize));
    }

    @Transactional
    public void deleteMembers(String... ids) {
        memberRepo.deleteMembers(ids);
    }
    
    @Transactional
    public long loadMembersFromFile(String filePath) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines()
                    .map(this::parseMember)
                    .map(this::saveMember)
                    .count();
        }
    }

    /**
     * memberLine format: id|firstName|middleName|lastName|email|mobile|dob
     * dob format: yyyy-MM-dd
     */
    private Member parseMember(String memberLine) {
        String[] items = memberLine.split("\\|");
        if (items.length < 7) {
            throw new IllegalArgumentException(String.format("Invalid member-line format: %s", memberLine));
        }

        Member member = new Member();
        member.setId(items[0]);
        member.setName(new Name(items[1], items[2], items[3]));
        member.setEmail(items[4]);
        member.setMobile(items[5]);
        member.setDob(new Date(java.sql.Date.valueOf(items[6]).getTime()));
        return member;
    }
}
