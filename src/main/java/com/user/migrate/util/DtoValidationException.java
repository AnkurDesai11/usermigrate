package com.user.migrate.util;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.user.migrate.dto.UserDto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class DtoValidationException extends Exception {

	private Set<ConstraintViolation<UserDto>> violations;
	private UserDto userDto;
}
