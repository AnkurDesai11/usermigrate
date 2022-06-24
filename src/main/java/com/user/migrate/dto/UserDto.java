package com.user.migrate.dto;

import java.util.Date;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

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
public class UserDto {
	
	//private Long id;
	
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	@Email
	private String email;
	
	@NotNull
	private String phone;
	
	private String country;
	
	private String profile;
	
	private String profileName;
	
	private Date dob;
	
}
