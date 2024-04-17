package com.aspire.loanbe.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.aspire.loanbe.dto.UserRequest;
import com.aspire.loanbe.model.Member;
import com.aspire.loanbe.repository.UserRepository;

@Service
public class UserService {
	private final UserRepository memberRespository;

	public UserService(UserRepository memberRespository) {
		this.memberRespository = memberRespository;
	}

	public Member createUser(UserRequest userRequest) {
		// Create a new User object from the UserRequest data
		Member newUser = new Member(userRequest.getUsername(), userRequest.isAdmin());

		// Save the new user in the database
		return memberRespository.save(newUser);
	}

	public Optional<Member> getCustomerById(Long userId) {
		return memberRespository.findById(userId);
	}

	public boolean isValidMember(Long userId) {
		return memberRespository.findById(userId).isPresent();
	}

}
