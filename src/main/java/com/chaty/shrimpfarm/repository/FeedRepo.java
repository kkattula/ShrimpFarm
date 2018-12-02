package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.Feed;

@RepositoryRestResource(collectionResourceRel = "feed", path = "feed")
public interface FeedRepo extends MongoRepository<Feed, String> {

}
