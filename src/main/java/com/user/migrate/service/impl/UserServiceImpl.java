package com.user.migrate.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.user.migrate.dto.UserDto;
import com.user.migrate.model.User;
import com.user.migrate.repo.UserRepository;
import com.user.migrate.service.FileService;
import com.user.migrate.service.UserService;
import com.user.migrate.util.UserFoundException;
import com.user.migrate.util.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileService fileService;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	//creating user
	@Override
	public UserDto createUser(UserDto userDto,MultipartFile profile) throws UserFoundException{
		// TODO Auto-generated method stub
		//return null;
		User user = this.modelMapper.map(userDto, User.class);
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
			if (!profile.isEmpty()){ 
				user.setProfile(this.fileService.uploadFileCloud(userDto.getUsername(), profile));
				user.setProfileName(profile.getOriginalFilename());
			}
			else {
				user.setProfile("default.png");
				user.setProfileName("default.png");
			}
			localUser = this.userRepository.save(user);
			localUser.setPassword(null);
		}
		return this.modelMapper.map(localUser, UserDto.class);
	}
	
	//getting user by username
	@Override
	public UserDto getUser(String username) throws ResourceNotFoundException{
		User user = this.userRepository.findByUsername(username);//.orElseThrow(()->new ResourceNotFoundException("user", "username", username));
		if (user == null)
			throw new ResourceNotFoundException("user", "username", username);
		else
			return this.modelMapper.map(user, UserDto.class);
	}
	
	//delete user by userId
	@Override
	public String deleteUser(String username) {
		try {
			this.userRepository.deleteById(this.userRepository.findByUsername(username).getId());
			return "Successfully Deleted";
		}catch(Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "Failed - "+sw.toString();
		}
	}

	//update user by username
	@Override
	public UserDto updateUser(UserDto userDto) throws ResourceNotFoundException {
		// TODO Auto-generated method stub
		
		User localUser = this.userRepository.findByUsername(userDto.getUsername());
		
		if(localUser==null) {
			System.out.println("User does not exist in database");
			throw new ResourceNotFoundException("User", "email", userDto.getEmail());
		}
		else {
			localUser.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
			localUser.setProfile(userDto.getProfile());
			localUser.setFirstName(userDto.getFirstName());
			localUser.setLastName(userDto.getLastName());
		}
		User updatedUser = this.userRepository.save(localUser);
		updatedUser.setPassword("");
		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	@Override
	public String createUsers(List<UserDto> usersToMigrate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}
}
