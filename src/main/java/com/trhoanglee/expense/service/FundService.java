package com.trhoanglee.expense.service;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.trhoanglee.expense.domain.Fund;
import com.trhoanglee.expense.repository.FundRepository;

/**
 * @author hoangtle
 */
@Service
@Transactional(readOnly = true)
public class FundService {
    private final AtomicInteger idGeneration = new AtomicInteger(1000);
    
    @Autowired
    private FundRepository fundRepo;
    
    public String idGenerationIncrementAndGet() {
        return String.valueOf(idGeneration.incrementAndGet());
    }
    
    public List<Fund> search(String keyword, int page, int pageSize) {
        keyword = (keyword == null) ? "" : keyword.toLowerCase();
        return fundRepo.searchFunds(keyword, new PageRequest(page, pageSize));
    }

    public Fund getFund(String id) {
        return fundRepo.findOne(id);
    }

    @Transactional
    public Fund saveFund(Fund expense) {
        if (expense == null) {
            return null;
        }
        if (expense.getId() == null) {
            expense.setId(idGenerationIncrementAndGet());
        }
        return fundRepo.save(expense);
    }

    @Transactional
    public void deleteFunds(String... ids) {
        fundRepo.deleteFunds(ids);
    }
    
    public void deleteAllFunds() {
        fundRepo.deleteAllInBatch();
    }

}
