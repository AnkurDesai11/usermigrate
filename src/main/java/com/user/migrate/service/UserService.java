package com.user.migrate.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.user.migrate.dto.UserDto;

public interface UserService {
	
	public UserDto createUser(UserDto userDto,  MultipartFile profile) throws Exception;
	
	public String createUsers(List<UserDto> usersToMigrate);

	public UserDto getUser(String username) throws Exception;
	
	public List<UserDto> getUsers();
	
	public UserDto updateUser(UserDto userDto) throws Exception;
	
	public String deleteUser(String username);
	
}
