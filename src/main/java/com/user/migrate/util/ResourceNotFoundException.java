package com.user.migrate.util;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException extends Exception {

	private String resourceName;
	private String fieldName;
	private String fieldId;
	
	public ResourceNotFoundException(String resourceName, String fieldName, String fieldId) {
		super(String.format("%s not found with %s : %s", resourceName, fieldName, fieldId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldId = fieldId;
	}
	
}
