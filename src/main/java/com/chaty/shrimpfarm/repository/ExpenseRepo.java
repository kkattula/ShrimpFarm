package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Expense;

@RepositoryRestResource(collectionResourceRel = "expense", path = "expense")
public interface ExpenseRepo extends MongoRepository<Expense, String> {

}
