package com.chaty.shrimpfarm.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.chaty.shrimpfarm.model.User;

@RepositoryRestResource(collectionResourceRel = "user", path = "user")
public interface UserRepo extends MongoRepository<User, String> {
	User findByUsername(String username);
	User findByEmail(String email);

}
