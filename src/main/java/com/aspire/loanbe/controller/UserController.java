package com.aspire.loanbe.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.aspire.loanbe.dto.UserRequest;
import com.aspire.loanbe.model.Member;
import com.aspire.loanbe.service.IUserService;

import jakarta.validation.Valid;

@RestController
public class UserController {
	private final IUserService userService;

	public UserController(IUserService userService) {
		this.userService = userService;
	}

	@PostMapping("/users")
	public ResponseEntity<?> createUser(@Valid @RequestBody UserRequest userRequest) {
		try {
			Member newUser = userService.createUser(userRequest);
			return new ResponseEntity<>(newUser, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>("Failed to create user: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
}
