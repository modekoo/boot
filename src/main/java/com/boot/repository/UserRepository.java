package com.boot.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.boot.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, String>{
	
	User findByUserIdAndPassword(String userId, String password);
	
	User findByUserId(String userId);
	
}