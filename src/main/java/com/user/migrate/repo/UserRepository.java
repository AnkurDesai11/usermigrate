package com.user.migrate.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.user.migrate.model.User;

public interface UserRepository extends JpaRepository<User, Long>{

	public User findByUsername(String username);
	
	public User findByEmail(String email);
	
}
