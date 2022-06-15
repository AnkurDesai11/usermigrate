package com.user.migrate.service;

import com.user.migrate.model.User;

public interface UserService {
	
	public User createUser(User user) throws Exception;

	public User getUser(String username);

	public void deleteUser(Long userId);
	
	public User updateUser(User user) throws Exception;
	
}
