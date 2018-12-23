package com.chaty.shrimpfarm.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.chaty.shrimpfarm.model.Expense;
import com.chaty.shrimpfarm.repository.ExpenseRepo;

@RestController
@RequestMapping("/farm/api/expense")
public class ExpenseController {

	@Autowired
	ExpenseRepo expenseRepo;


	@RequestMapping(method = RequestMethod.POST)
	public Expense addExpenseEntry(@Valid @RequestBody Expense entry) {
		return expenseRepo.save(entry);
	}

	@RequestMapping(path = "/list", method = RequestMethod.POST)
	public void addExpenseList(@Valid @RequestBody List<Expense> entry) {
		entry.stream().forEach(a -> expenseRepo.save(a));
	}

	@RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
	public void deleteExpense(@PathVariable("id") String id) {
		final Expense feed = expenseRepo.findOne(id);
		expenseRepo.delete(feed);
	}

	@RequestMapping(path = "/list", method = RequestMethod.GET)
	public List<Expense> getExpenseList() {
		return expenseRepo.findAll();
	}
}
