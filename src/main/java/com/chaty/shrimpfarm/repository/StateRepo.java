package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.farmloader.State;

@RepositoryRestResource(collectionResourceRel = "state", path = "state")
public interface StateRepo extends MongoRepository<State, String> {

}
