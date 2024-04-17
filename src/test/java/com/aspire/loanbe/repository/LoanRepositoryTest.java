package com.aspire.loanbe.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.aspire.loanbe.model.Loan;
import com.aspire.loanbe.model.Member;
import com.aspire.loanbe.model.StatusEnum;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@DataJpaTest
public class LoanRepositoryTest {

	@Autowired
	private LoanRepository loanRepository;

	@Autowired
	private UserRepository userRepository;

	private Loan loan;
	private Member member;

	@BeforeEach
	void setUp() {
		member = new Member("Suraj", false);
		userRepository.save(member);
		loan = new Loan(member, 100, 2, StatusEnum.PENDING, new Date());
		loanRepository.save(loan);
	}

	@AfterAll
	void tearDown() {
		member = null;
		loan = null;
		loanRepository.deleteAll();
		userRepository.deleteAll();
	}

	@Test
	void testfindByCustomerIdFound() {
		List<Loan> loans = loanRepository.findByCustomerId((long) 1);
		assertThat(loans.get(0).getState()).isEqualTo(loan.getState());
		assertThat(loans.get(0).getAmount()).isEqualTo(loan.getAmount());
	}

	@Test
	void testfindByCustomerIdNotFound() {
		List<Loan> loans = loanRepository.findByCustomerId((long) 500);
		assertThat(loans.isEmpty()).isTrue();
	}

}
