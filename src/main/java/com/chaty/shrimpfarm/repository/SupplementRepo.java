package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Supplement;

@RepositoryRestResource(collectionResourceRel = "supplement", path = "supplement")
public interface SupplementRepo extends MongoRepository<Supplement, String> {

}
