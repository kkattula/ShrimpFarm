package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Activity;

@RepositoryRestResource(collectionResourceRel = "activity", path = "activity")
public interface ActivityRepo extends MongoRepository<Activity, String> {

}
