package com.hoangminh.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hoangminh.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long>{
	
////	@Query(value="select u from user")
		List<User> findAll();
////	
////	@Query(value="select u from user where u.id = :id")
		Optional<User> findById( Long id);
//	
////	@Query(value="select u from user where u.username = :username")
		Optional<User> findByUsername(String username);
	
}
