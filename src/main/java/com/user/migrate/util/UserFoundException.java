package com.user.migrate.util;

public class UserFoundException  extends  Exception{

	private String message;
    public UserFoundException() {
        this.message="User already exists in database";
    }
	public UserFoundException(String message) {
		this.message = message;
	}
	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}
	/**
	 * @param message the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}