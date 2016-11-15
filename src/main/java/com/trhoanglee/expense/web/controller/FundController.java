package com.trhoanglee.expense.web.controller;

import static org.springframework.web.bind.annotation.RequestMethod.DELETE;
import static org.springframework.web.bind.annotation.RequestMethod.GET;
import static org.springframework.web.bind.annotation.RequestMethod.POST;
import static org.springframework.web.bind.annotation.RequestMethod.PUT;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.trhoanglee.expense.domain.Fund;
import com.trhoanglee.expense.service.FundService;
import com.trhoanglee.expense.web.dto.FundInfo;

@RestController
@RequestMapping(value = "/api/funds")
public class FundController {

	@Autowired
	private FundService fundService;

	@RequestMapping(method = GET)
	public List<FundInfo> searchFunds(
			@RequestParam(defaultValue="") String keyword, 
			@RequestParam(defaultValue="0") int page, 
			@RequestParam(defaultValue="10") int pageSize) {
		List<Fund> expenses = fundService.search(keyword, page, pageSize);
		return convertToDto(expenses);
	}


    @RequestMapping(value = "/{id}", method = GET)
	public FundInfo getFund(@PathVariable("id") String id) {
	    Fund expense = fundService.getFund(id);
        return convertToDto(expense);
	}  

    @RequestMapping(method = POST)
	@ResponseStatus(HttpStatus.CREATED)
	public FundInfo createFund(@RequestBody FundInfo expense) {
		expense.setId(null);
		Fund expenseEntity = convertToEntity(expense);
		expenseEntity = fundService.saveFund(expenseEntity);
		return convertToDto(expenseEntity);
	}


    @RequestMapping(value = "/{id}", method = PUT)
	@ResponseStatus(HttpStatus.ACCEPTED)
	public FundInfo updateFund(
			@PathVariable("id") String id, 
			@RequestBody FundInfo expense) {
		expense.setId(id);
        Fund expenseEntity = convertToEntity(expense);
        expenseEntity = fundService.saveFund(expenseEntity);
        return convertToDto(expenseEntity);
    }
	
	@RequestMapping(method = DELETE)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteContacts(@RequestParam String[] ids) {
	    fundService.deleteFunds(ids);
    }
	
    private List<FundInfo> convertToDto(List<Fund> expenses) {
        List<FundInfo> response = new ArrayList<>();
        expenses.forEach(expense -> {
            response.add(convertToDto(expense));
        });
        return response;
    }
    
    private FundInfo convertToDto(Fund expense) {
        FundInfo response = new FundInfo();
        BeanUtils.copyProperties(expense, response);
        return response;
    }
    
    private Fund convertToEntity(FundInfo expense) {
        Fund expenseEntity = new Fund();
        BeanUtils.copyProperties(expense, expenseEntity);
        return expenseEntity;
    }
}
