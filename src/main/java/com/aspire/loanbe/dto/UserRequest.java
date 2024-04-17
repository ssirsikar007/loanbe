package com.aspire.loanbe.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserRequest {

	@NotNull(message = "User name cannot be null")
	@NotBlank(message = "User name cannot be blank")
	private String username;
	private boolean isAdmin = false;

	public UserRequest(String username, boolean isAdmin) {
		super();
		this.username = username;
		this.isAdmin = isAdmin;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public boolean isAdmin() {
		return isAdmin;
	}

	public void setAdmin(boolean isAdmin) {
		this.isAdmin = isAdmin;
	}

}
