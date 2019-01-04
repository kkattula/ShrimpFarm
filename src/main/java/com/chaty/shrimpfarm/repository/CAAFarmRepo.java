package com.chaty.shrimpfarm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.farmloader.CAAFarm;

@RepositoryRestResource(collectionResourceRel = "caafarm", path = "caafarm")
public interface CAAFarmRepo extends MongoRepository<CAAFarm, String> {
	List<CAAFarm> getByUuid(String uuid);

}
