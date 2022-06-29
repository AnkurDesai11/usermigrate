package com.user.migrate.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserDto {
	
	//private Long id;
	
	@NotEmpty(message = "Username cannot be empty")
	@Pattern(regexp = "^[A-Za-z0-9]+$", message = "Username must be alphanumeric")
	private String username;
	
	@NotEmpty(message = "Password cannot be empty")
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	@Email(message = "Email must be valid")
	private String email;
	
	@NotEmpty(message = "Phone cannot be empty")
	private String phone;
	
	private String country;
	
	private String profile;
	
	private String profileName;
	
	private Date dob;
	
}
