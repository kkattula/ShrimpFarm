package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Harvest;

@RepositoryRestResource(collectionResourceRel = "harvest", path = "harvest")
public interface HarvestRepo extends MongoRepository<Harvest, String> {

}
