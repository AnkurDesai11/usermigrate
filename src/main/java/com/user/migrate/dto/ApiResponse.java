package com.user.migrate.dto;

import java.util.HashMap;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ApiResponse {
	private String message;
	private String success;
	private HashMap<String, UserDto> usersAdded;
	private HashMap<String, HashMap<Integer,String>> errors;
}
