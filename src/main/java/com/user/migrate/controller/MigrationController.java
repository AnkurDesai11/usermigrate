package com.user.migrate.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.user.migrate.model.User;
import com.user.migrate.service.UserService;



@RestController
@RequestMapping("/users")
@CrossOrigin("*")
public class MigrationController {
		
		@Autowired
		private UserService userService;
		
		//creating user
		@PostMapping("/")
		public User createUser(@RequestBody User user) throws Exception {
			return this.userService.createUser(user);
		}
		
		@GetMapping("/{username}")
		public User getUser(@PathVariable("username") String username) {
			return this.userService.getUser(username);
		}
		
		//delete user by userId
		@DeleteMapping("/{userId}")
		public void deleteUser(@PathVariable("userId") Long userId) {
			this.userService.deleteUser(userId);
		}
		
		//update user by username
		@PutMapping("/")
		public User updateUser(@RequestBody User user) throws Exception {
			return this.userService.updateUser(user);
		}
		
}
