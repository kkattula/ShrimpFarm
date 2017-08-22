package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.FeedEntryModel;

@RepositoryRestResource(collectionResourceRel = "feedEntry", path = "feedEntry")
public interface FeedEntryRepo extends MongoRepository<FeedEntryModel, String> {

}
