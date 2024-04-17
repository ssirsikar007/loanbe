package com.aspire.loanbe.service;

import com.aspire.loanbe.dto.UserRequest;
import com.aspire.loanbe.model.Member;

public interface IUserService {
	public Member createUser(UserRequest userRequest);
}
