package com.user.migrate.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.migrate.config.AppConstantsConfig;
import com.user.migrate.dto.ApiResponse;
import com.user.migrate.dto.UserDto;
import com.user.migrate.service.UserService;
import com.user.migrate.util.DtoValidationException;


@RestController
@RequestMapping("/source1/api/users")
@CrossOrigin("*")
public class MigrationController {
		
		@Autowired
		private UserService userService;
		
		@Autowired
		private Validator validator;
		
		//create user
		@PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<?> createUser(@RequestPart(value = "profile",required = false) MultipartFile profile, @RequestPart(value="user") String userString) throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			UserDto userDto = mapper.readValue(userString, UserDto.class);
			Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
			System.out.println(violations);
			if(!violations.isEmpty())
				throw new DtoValidationException(violations, userDto);
			UserDto savedUserDto = this.userService.createUser(userDto, profile);
			return new ResponseEntity<>(new ApiResponse("user created", "true", new HashMap <String, UserDto>(){{ put(savedUserDto.getUsername(), savedUserDto); }}, null) ,HttpStatus.CREATED);
		}
		
		//create multiple users
		@PostMapping(value = "/import")
		public ResponseEntity<?> createUsers(@RequestBody List<UserDto> usersDto) throws Exception {
			return new ResponseEntity<>(this.userService.createUsers(usersDto), HttpStatus.OK);
		}
		
		//read user
		@GetMapping("/{username}")
		public ResponseEntity<?> getUser(@PathVariable("username") String username, @RequestHeader("x-okta-verification-challenge") String verificationHeader) throws Exception {
			System.out.println(verificationHeader);
			return new ResponseEntity<>(this.userService.getUser(username),HttpStatus.OK);
		}
		
		//read multiple users
		@GetMapping("/")
		public ResponseEntity<?> getUsers(@RequestParam(value="pageNumber", defaultValue = AppConstantsConfig.PAGE_NUMBER, required = false) Integer pageNumber, 
										  @RequestParam(value="pageSize", defaultValue = AppConstantsConfig.PAGE_SIZE, required = false) Integer pageSize,
										  @RequestParam(value="pageSortBy", defaultValue = AppConstantsConfig.SORT_BY, required = false) String pageSortBy,
										  @RequestParam(value="pageSortDir", defaultValue = AppConstantsConfig.SORT_DIRECTION, required = false) String pageSortDir,
										  @RequestParam(value="keyword", defaultValue = AppConstantsConfig.KEYWORD, required = false) String keyword,
										  @RequestParam(value="field", defaultValue = AppConstantsConfig.FIELD, required = false) String field) throws Exception {
			return new ResponseEntity<>(this.userService.getUsers(pageNumber, pageSize, pageSortBy, pageSortDir, keyword, field),HttpStatus.OK);
		}
		
		//update user by username
		@PutMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<?> updateUser(@RequestPart(value = "profile",required = false) MultipartFile profile, @RequestPart(value="user") String userString) throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			UserDto userDto = mapper.readValue(userString, UserDto.class);
			Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
			if(!violations.isEmpty())
				throw new DtoValidationException(violations, userDto);
			UserDto updatedUserDto = this.userService.updateUser(userDto, profile);
			return new ResponseEntity<>(new ApiResponse("user updated", "true", new HashMap <String, UserDto>(){{ put(updatedUserDto.getUsername(), updatedUserDto); }}, null) ,HttpStatus.OK);
		}
		
		//delete user by username
		@DeleteMapping("/{username}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable("username") String username) throws Exception {
			ApiResponse result = this.userService.deleteUser(username);
			if(result.getSuccess() == "true")
				return new ResponseEntity<>(result, HttpStatus.OK);
			else
				return new ResponseEntity<>(result, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
}
