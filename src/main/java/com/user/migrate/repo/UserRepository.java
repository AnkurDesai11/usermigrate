package com.user.migrate.repo;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.user.migrate.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
	@Query(value = "select user from User user where user.username like :username")
	public Page<User> findByUsername(@Param("username")String username, Pageable pageInfo);
	
	@Query(value = "select user from User user where user.email like :email")
	public Page<User> findByEmail(@Param("email")String email, Pageable pageInfo);
	
	@Query(value = "select user from User user where user.profile like :profile")
	public Page<User> findByProfile(@Param("profile")String profile, Pageable pageInfo);
	
	@Query(value = "select user from User user where user.country like :country")
	public Page<User> findByCountry(@Param("country")String country, Pageable pageInfo);
	
}
