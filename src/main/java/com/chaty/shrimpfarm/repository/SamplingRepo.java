package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Sampling;

@RepositoryRestResource(collectionResourceRel = "sampling", path = "sampling")
public interface SamplingRepo extends MongoRepository<Sampling, String> {

}
