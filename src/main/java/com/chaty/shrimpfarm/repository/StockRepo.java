package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Stock;

@RepositoryRestResource(collectionResourceRel = "stock", path = "stock")
public interface StockRepo extends MongoRepository<Stock, String> {

}
