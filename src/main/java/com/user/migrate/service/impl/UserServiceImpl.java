package com.user.migrate.service.impl;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.user.migrate.dto.ApiResponse;
import com.user.migrate.dto.UserDto;
import com.user.migrate.model.User;
import com.user.migrate.repo.UserRepository;
import com.user.migrate.service.FileService;
import com.user.migrate.service.UserService;
import com.user.migrate.util.UserFoundException;
import com.user.migrate.util.ResourceNotFoundException;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private FileService fileService;

	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private Validator validator;

	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	// creating user
	@Override
	public UserDto createUser(UserDto userDto, MultipartFile profile) throws UserFoundException {
		// TODO Auto-generated method stub
		// return null;
		User user = this.modelMapper.map(userDto, User.class);
		User localUser = this.userRepository.findByUsername(user.getUsername());
		User localUsermail = this.userRepository.findByEmail(user.getEmail());

		if (localUser != null) {
			System.out.println("User with username exists in database");
			// throw new Exception("User with username exists");
			throw new UserFoundException("User with username already exists in database");
		} else if (localUsermail != null) {
			System.out.println("User with email exists in database");
			throw new UserFoundException("User with email already exists in database");
		} else {
			// create user
			user.setPassword(this.passwordEncoder.encode(user.getPassword()));
			if (!profile.isEmpty()) {
				user.setProfileName(profile.getOriginalFilename());
				user.setProfile(this.fileService.uploadFileCloud(userDto.getUsername(), profile));
			} else {
				user.setProfileName("default.png");
				user.setProfile("default.png");
			}
			localUser = this.userRepository.save(user);
			localUser.setPassword(null);
		}
		return this.modelMapper.map(localUser, UserDto.class);
	}

	@Override
	public ApiResponse createUsers(List<UserDto> usersToMigrate) {
//		List <User> usersToSave = new ArrayList<User>();
		HashMap<String, HashMap<Integer, String>> responseMap = new HashMap<>();
		HashMap<String, UserDto> users = new HashMap<>();
		String status = "true";
//		try {
//			usersToSave = usersToMigrate.stream()
//					.map(userDto -> this.modelMapper.map(userDto, User.class))
//					.collect(Collectors.toList());
//			this.userRepository.saveAll(usersToSave);
//			for (User user : usersToSave) 
//		        responseMap.put(user.getUsername(), "true");
//			return new ApiResponse("All records entered sucessfully", status, responseMap);
//			//usersToSave.stream().collect(Collectors.toMap(User::getUsername, "true")));
//		}catch(Exception e) {
		for (UserDto userDto : usersToMigrate) {
			HashMap<Integer, String> errors = new HashMap<>();
			int i = 0;
			try {
				Set<ConstraintViolation<UserDto>> violations = validator.validate(userDto);
				for(ConstraintViolation<UserDto> error : violations){
					int fieldName = ++i;
					String message = error.getPropertyPath().toString()+" : "+error.getMessage().toString();
					errors.put(fieldName, message);
				}
				User currentUser = this.modelMapper.map(userDto, User.class);
				User localUsername = this.userRepository.findByUsername(currentUser.getUsername());
				User localUsermail = this.userRepository.findByEmail(currentUser.getEmail());
				
				if (localUsername != null) {
					status = "partial";
					errors.put(++i, "username : username already exists");
				} else if (localUsermail != null) {
					status = "partial";
					errors.put(++i, "email : email already exists");
				} else {
					UserDto savedUser = this.modelMapper.map(this.userRepository.save(currentUser), UserDto.class);
					savedUser.setPassword(null);
					users.put(userDto.getUsername(),savedUser);
				}
			} catch (Exception ee) {
				status = "partial";
				errors.put(++i, "Error in parsing and saving user");
			}
			responseMap.put(userDto.getUsername(), errors);
		}
		
		return new ApiResponse(
				status == "true" ? "All records entered sucessfully" : 
					users.isEmpty()?"Failed to import all records":"Failed to import some records",
				users.isEmpty()?"false":status, 
				users, responseMap);
//	}
	}

	// getting user by username
	@Override
	public UserDto getUser(String username) throws ResourceNotFoundException {
		User user = this.userRepository.findByUsername(username);// .orElseThrow(()->new
																	// ResourceNotFoundException("user", "username",
																	// username));
		if (user == null) {
			throw new ResourceNotFoundException("user", "username", username);
		} else {
			user.setPassword(null);
			return this.modelMapper.map(user, UserDto.class);
		}
	}

	@Override
	public List<UserDto> getUsers() {
		// TODO Auto-generated method stub
		return null;
	}

	// update user by username
	@Override
	public UserDto updateUser(UserDto userDto, MultipartFile profile) throws Exception {
		// TODO Auto-generated method stub

		User localUser = this.userRepository.findByUsername(userDto.getUsername());
		User updatedUser = new User();
		if (localUser == null) {
			System.out.println("User does not exist in database");
			throw new ResourceNotFoundException("User", "username", userDto.getUsername());
		} else {
			updatedUser = this.modelMapper.map(userDto, User.class);
			updatedUser.setId(localUser.getId());
			updatedUser.setPassword(this.passwordEncoder.encode(updatedUser.getPassword()));
			if (!profile.isEmpty()) {
				if (localUser.getProfile() != "default.png")
					this.fileService.deleteFileCloud(localUser.getProfile());
				updatedUser.setProfileName(profile.getOriginalFilename());
				updatedUser.setProfile(this.fileService.uploadFileCloud(userDto.getUsername(), profile));
			} else {
				updatedUser.setProfileName(localUser.getProfileName());
				updatedUser.setProfile(localUser.getProfile());
			}
//			localUser.setPassword(this.passwordEncoder.encode(userDto.getPassword()));
//			localUser.setFirstName(userDto.getFirstName());
//			localUser.setLastName(userDto.getLastName());
		}
		updatedUser = this.userRepository.save(updatedUser);
		updatedUser.setPassword("");
		return this.modelMapper.map(updatedUser, UserDto.class);
	}

	// delete user by userId
	@Override
	public String deleteUser(String username) {
		try {
			User user = this.userRepository.findByUsername(username);
			if (user.getProfile() == "default.png") {
				this.userRepository.deleteById(user.getId());
				return "Delete successful";
			} else {
				if (this.fileService.deleteFileCloud(user.getProfile())) {
					this.userRepository.deleteById(user.getId());
					return "Delete Successful";
				}
			}

			return "Delete failed";
		} catch (Exception e) {
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			e.printStackTrace(pw);
			return "Failed - " + sw.toString();
		}
	}

}
