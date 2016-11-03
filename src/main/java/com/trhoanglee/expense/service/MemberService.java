package com.trhoanglee.expense.service;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Date;
import java.util.List;

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
    @Autowired
    private MemberRepository memberRepo;
    
    @Transactional
    public Member saveMember(@Valid Member member) {
    	if (member == null) {
    		return null;
    	}
        return memberRepo.save(member);
    }

    @Transactional
    public void deleteAllMembers() {
        memberRepo.deleteAllInBatch();
    }

    public Member getMember(Long id) {
        return memberRepo.findOne(id);
    }

    public List<Member> search(String keyword, int page, int pageSize) {
        keyword = (keyword == null) ? "" : keyword.toLowerCase();
        return memberRepo.searchMembers(keyword, new PageRequest(page, pageSize));
    }

    @Transactional
    public void deleteMembers(Long... ids) {
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
     * memberLine format: id|firstName|middleName|lastName|email|mobile|dob dob
     * format: yyyy-MM-dd
     */
    private Member parseMember(String memberLine) {
        String[] items = memberLine.split("\\|");
        if (items.length < 7) {
            throw new IllegalArgumentException(String.format("Invalid member-line format: %s", memberLine));
        }

        Member member = new Member();
        try {
            member.setId(Long.parseLong(items[0]));
        } catch (NumberFormatException ex) {
            throw new IllegalArgumentException(String.format("Invalid member-line format: %s", memberLine, ex));
        }
        member.setName(new Name(items[1], items[2], items[3]));
        member.setEmail(items[4]);
        member.setMobile(items[5]);
        member.setDob(new Date(java.sql.Date.valueOf(items[6]).getTime()));
        return member;
    }

}
