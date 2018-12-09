package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Pond;

@RepositoryRestResource(collectionResourceRel = "pond", path = "pond")
public interface PondRepo extends MongoRepository<Pond, String> {

}
