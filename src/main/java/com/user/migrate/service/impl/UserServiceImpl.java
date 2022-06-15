package com.user.migrate.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.user.migrate.model.User;
import com.user.migrate.repo.UserRepository;
import com.user.migrate.service.UserService;
import com.user.migrate.util.UserFoundException;
import com.user.migrate.util.UserNotFoundException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;
	
	private BCryptPasswordEncoder passwordEncoder;
	//creating user
	@Override
	public User createUser(User user) throws UserFoundException{
		// TODO Auto-generated method stub
		//return null;
		
		User localUser = this.userRepository.findByUsername(user.getUsername());
		User localUsermail = this.userRepository.findByEmail(user.getEmail());
		
		if(localUser!=null) {
			System.out.println("User with username exists in database");
			//throw new Exception("User with username exists");
			throw new UserFoundException("User with username already exists in database");
		}
		else if(localUsermail!=null) {
			System.out.println("User with email exists in database");
			throw new UserFoundException("User with email already exists in database");
		}
		else {
			//create user
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			localUser = this.userRepository.save(user);
		}
		return localUser;
	}
	
	//getting user by username
	@Override
	public User getUser(String username) {
		return this.userRepository.findByUsername(username);
	}
	
	//delete user by userId
	@Override
	public void deleteUser(Long userId) {
		this.userRepository.deleteById(userId);
	}

	//update user by username
	@Override
	public User updateUser(User user) throws UserNotFoundException {
		// TODO Auto-generated method stub
		
		User localUser = this.userRepository.findByUsername(user.getUsername());
		
		if(localUser==null) {
			System.out.println("User does not exist in database");
			throw new UserNotFoundException();
		}
		else {
			user.setId(localUser.getId());
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			user.setProfile(localUser.getProfile());
		}
		User updatedUser = this.userRepository.save(user);
		updatedUser.setPassword("");
		return updatedUser;
	}
}
