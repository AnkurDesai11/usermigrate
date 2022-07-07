package com.user.migrate.service;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.user.migrate.dto.ApiResponse;
import com.user.migrate.dto.PagedUsersResponse;
import com.user.migrate.dto.UserDto;

public interface UserService {
	
	public UserDto createUser(UserDto userDto, MultipartFile profile) throws Exception;
	
	public ApiResponse createUsers(List<UserDto> usersToMigrate) throws Exception;

	public UserDto getUser(String username) throws Exception;
	
	public PagedUsersResponse getUsers(Integer pageNumber, Integer pageSize, String pageSortBy, String pageSortDir, String keyword, String field);
	
	public UserDto updateUser(UserDto userDto, MultipartFile profile) throws Exception;
	
	public ApiResponse deleteUser(String username) throws Exception;
	
}
