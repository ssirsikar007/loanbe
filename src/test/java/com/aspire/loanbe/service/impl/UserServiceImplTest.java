package com.aspire.loanbe.service.impl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aspire.loanbe.dto.UserRequest;
import com.aspire.loanbe.model.Member;
import com.aspire.loanbe.repository.UserRepository;
import com.aspire.loanbe.service.IUserService;

class UserServiceImplTest {

	@Mock
	private UserRepository userRepository;
	private IUserService userService;
	AutoCloseable autoClosable;
	Member member;

	@BeforeEach
	void setUp() {
		autoClosable = MockitoAnnotations.openMocks(this);
		userService = new UserServiceImpl(userRepository);
		member = new Member("Suraj");
	}

	@AfterEach
	void tearDown() throws Exception {
		autoClosable.close();
	}

	@Test
	void testCreateUserSuccessfull() {
		mock(Member.class);
		mock(UserRepository.class);

		when(userRepository.save(any(Member.class))).thenReturn(member);
		assertThat(userService.createUser(new UserRequest("Suraj", false)).getUsername())
				.isEqualTo(member.getUsername());
	}

}
