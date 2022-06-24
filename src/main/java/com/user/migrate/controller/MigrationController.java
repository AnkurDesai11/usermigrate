package com.user.migrate.controller;

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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.user.migrate.dto.ApiResponse;
import com.user.migrate.dto.UserDto;
import com.user.migrate.service.UserService;



@RestController
@RequestMapping("/source1/api/users")
@CrossOrigin("*")
public class MigrationController {
		
		@Autowired
		private UserService userService;
		
		//creating user
		@PostMapping(value = "/", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
		public ResponseEntity<UserDto> createUser(@RequestPart(value = "profile",required = false) MultipartFile profile, @RequestPart(value="user") String userString) throws Exception {
			ObjectMapper mapper = new ObjectMapper();
			UserDto userDto = mapper.readValue(userString, UserDto.class);
			return new ResponseEntity<>(this.userService.createUser(userDto, profile),HttpStatus.CREATED);
		}
		
		//read this user
		@GetMapping("/{username}")
		public ResponseEntity<?> getUser(@PathVariable("username") String username) throws Exception {
			System.out.println(username);
			return new ResponseEntity<>(this.userService.getUser(username),HttpStatus.OK);
		}
		
		//read all users
		@GetMapping("/")
		public ResponseEntity<UserDto> getUsers(@PathVariable("username") String username) throws Exception {
			return new ResponseEntity<>(this.userService.getUser(username),HttpStatus.OK);
		}
		
		//update user by username
		@PutMapping("/")
		public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto) throws Exception {
			return new ResponseEntity<>(this.userService.updateUser(userDto),HttpStatus.OK);
		}
		
		//delete user by userId
		@DeleteMapping("/{username}")
		public ResponseEntity<ApiResponse> deleteUser(@PathVariable("username") String username) {
			String result = this.userService.deleteUser(username);
			if(result == "Successfully Deleted")
				return new ResponseEntity<>(new ApiResponse(result, true), HttpStatus.OK);
			else
				return new ResponseEntity<>(new ApiResponse(result, false), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
}
