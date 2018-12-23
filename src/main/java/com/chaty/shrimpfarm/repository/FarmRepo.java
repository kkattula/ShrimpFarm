package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Farm;

@RepositoryRestResource(collectionResourceRel = "farm", path = "farm")
public interface FarmRepo extends MongoRepository<Farm, String> {

}
