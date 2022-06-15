package com.user.migrate.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.persistence.Id;


import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name = "USERS_BASE")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String email;
	private String phone;
	private String country;
	
	@Column(columnDefinition="MEDIUMTEXT")
	private String profile;
	
	private String profileName;
	
	@JsonFormat(pattern = "yyyy-MM-dd")
    private Date dob;
	
	public User(Long id, String username, String password, String firstName, String lastName, String email,
			String phone, String country, Date dob) {
		super();
		this.id = id;
		this.username = username;
		this.password = password;
		this.firstName = firstName;
		this.lastName = lastName;
		this.email = email;
		this.phone = phone;
		this.country = country;
		this.dob = dob;
	}
	
	
}
