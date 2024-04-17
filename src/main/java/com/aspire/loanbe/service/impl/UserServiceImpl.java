package com.aspire.loanbe.service.impl;

import org.springframework.stereotype.Service;

import com.aspire.loanbe.dto.UserRequest;
import com.aspire.loanbe.model.Member;
import com.aspire.loanbe.repository.UserRepository;
import com.aspire.loanbe.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
	private final UserRepository memberRespository;

	public UserServiceImpl(UserRepository memberRespository) {
		this.memberRespository = memberRespository;
	}

	@Override
	public Member createUser(UserRequest userRequest) {
		// Create a new User object from the UserRequest data
		Member newUser = new Member(userRequest.getUsername(), userRequest.isAdmin());

		// Save the new user in the database
		return memberRespository.save(newUser);
	}

}
