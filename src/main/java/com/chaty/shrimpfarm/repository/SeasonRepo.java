package com.chaty.shrimpfarm.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Season;

@RepositoryRestResource(collectionResourceRel = "season", path = "season")
public interface SeasonRepo extends MongoRepository<Season, String> {
	List<Season> findByFarmUUID(String uuid);

}
