package com.aspire.loanbe.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.aspire.loanbe.dto.LoanRequest;
import com.aspire.loanbe.dto.UserLoanResponse;
import com.aspire.loanbe.exception.CustomerNotFoundException;
import com.aspire.loanbe.exception.InsufficientRepaymentAmountException;
import com.aspire.loanbe.exception.InvalidLoanException;
import com.aspire.loanbe.exception.LoanCompletedException;
import com.aspire.loanbe.exception.LoanNotBelongToUserException;
import com.aspire.loanbe.exception.NotAdminException;
import com.aspire.loanbe.model.Loan;
import com.aspire.loanbe.model.Member;
import com.aspire.loanbe.model.ScheduledRepayment;
import com.aspire.loanbe.model.StatusEnum;
import com.aspire.loanbe.repository.LoanRepository;
import com.aspire.loanbe.repository.ScheduledRepaymentRepository;
import com.aspire.loanbe.service.UserService;
import com.aspire.loanbe.util.LoanUtil;

class LoanServiceImplTest {

	@Mock
	private LoanRepository loanRepository;

	@Mock
	private ScheduledRepaymentRepository scheduledRepaymentRepository;

	@Mock
	private UserService userService;

	@InjectMocks
	private LoanServiceImpl loanService;

	@Mock
	private LoanUtil loanUtil;

	@BeforeEach
	void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test
	void testCreateLoan_Successful() {
		// Given
		LoanRequest loanRequest = new LoanRequest(10000.0, 12, new Date());
		Member customer = new Member();
		customer.setId(1L);
		when(userService.getCustomerById(1L)).thenReturn(Optional.of(customer));
		when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

		// When
		loanService.createLoan(loanRequest, 1L);

		// Then
		verify(userService).getCustomerById(1L);
		verify(loanRepository).save(any());
		verify(scheduledRepaymentRepository, times(12)).save(any());
	}

	@Test
	void testCreateLoan_CustomerNotFound() {
		// Given
		LoanRequest loanRequest = new LoanRequest(10000.0, 12, new Date());
		when(userService.getCustomerById(1L)).thenReturn(Optional.empty());

		// When
		assertThrows(CustomerNotFoundException.class, () -> loanService.createLoan(loanRequest, 1L));

		// Then
		verify(userService).getCustomerById(1L);
		verify(loanRepository, never()).save(any());
		verify(scheduledRepaymentRepository, never()).save(any());
	}

	@Test
	void testGetLoans_Successful() {
		List<Loan> loans = new ArrayList<>();
		loans.add(new Loan());
		when(userService.isValidMember(1L)).thenReturn(true);
		when(loanRepository.findByCustomerId(1L)).thenReturn(loans);
		when(loanUtil.mapToUserLoanResponse(loans)).thenReturn(new ArrayList<UserLoanResponse>());

		// When
		List<UserLoanResponse> userLoans = loanService.getLoans(1L);

		// Then
		assertNotNull(userLoans);
		assertEquals(1, userLoans.size());
	}

	@Test
	void testApproveLoan_Successful() {
		// Given
		Member admin = new Member("Suresh", true);
		admin.setId(1L);
		Optional<Member> optionalAdmin = Optional.of(admin);
		Optional<Loan> optionalLoan = Optional.of(new Loan());
		when(userService.getCustomerById(1L)).thenReturn(optionalAdmin);
		when(loanRepository.findById(1L)).thenReturn(optionalLoan);
		when(loanRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

		// When
		Loan approvedLoan = loanService.aproveLoan(1L, 1L);

		// Then
		assertNotNull(approvedLoan);
		assertEquals(StatusEnum.APPROVED, approvedLoan.getState());
	}

	@Test
    void testApproveLoan_NotAdminException() {
        // Given
        when(userService.getCustomerById(1L)).thenReturn(Optional.empty());

        // When & Then
        assertThrows(NotAdminException.class, () -> loanService.aproveLoan(1L, 1L));
    }

	@Test
	void testApproveLoan_NotAdmin() {
		// Given
		Member admin = new Member();
		admin.setId(1L);
		Optional<Member> optionalAdmin = Optional.of(admin);
		Optional<Loan> optionalLoan = Optional.of(new Loan());
		when(userService.getCustomerById(1L)).thenReturn(optionalAdmin);
		when(loanRepository.findById(1L)).thenReturn(optionalLoan);
		when(userService.getCustomerById(1L)).thenReturn(optionalAdmin); // Not an admin

		// When & Then
		assertThrows(NotAdminException.class, () -> loanService.aproveLoan(1L, 1L));
	}

	@Test
	void testRepay_Successful() {
		// Given
		Loan loan = new Loan();
		loan.setId(1L);
		loan.setState(StatusEnum.APPROVED);
		loan.setCustomer(new Member());
		loan.getCustomer().setId(1L);

		ScheduledRepayment repayment = new ScheduledRepayment();
		repayment.setLoan(loan);
		repayment.setState(StatusEnum.PENDING);
		repayment.setAmount(100.0);
		List<ScheduledRepayment> repayments = new ArrayList<>();
		repayments.add(repayment);

		when(userService.isValidMember(1L)).thenReturn(true);
		when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
		when(scheduledRepaymentRepository.getByLoanId(1L)).thenReturn(repayments);
		when(scheduledRepaymentRepository.save(any(ScheduledRepayment.class)))
				.thenAnswer(invocation -> invocation.getArgument(0));

		// When
		loanService.repay(1L, 100.0, 1L);

		// Then
		verify(userService).isValidMember(1L);
		verify(loanRepository).findById(1L);
		verify(scheduledRepaymentRepository).getByLoanId(1L);
		verify(scheduledRepaymentRepository).save(any(ScheduledRepayment.class));
	}

	@Test
	void testRepay_InsufficientRepaymentAmount() {
		// Given
		Loan loan = new Loan();
		loan.setId(1L);
		loan.setState(StatusEnum.APPROVED);
		loan.setCustomer(new Member());
		loan.getCustomer().setId(1L);

		ScheduledRepayment repayment = new ScheduledRepayment();
		repayment.setLoan(loan);
		repayment.setState(StatusEnum.PENDING);
		repayment.setAmount(100.0);
		List<ScheduledRepayment> repayments = new ArrayList<>();
		repayments.add(repayment);

		when(userService.isValidMember(1L)).thenReturn(true);
		when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));
		when(scheduledRepaymentRepository.getByLoanId(1L)).thenReturn(repayments);

		// When & Then
		assertThrows(InsufficientRepaymentAmountException.class, () -> loanService.repay(1L, 50.0, 1L));
	}

	@Test
	void testRepay_InvalidLoan() {
	    // Given
	    when(userService.isValidMember(1L)).thenReturn(true);
	    when(loanRepository.findById(1L)).thenReturn(Optional.empty());

	    // When & Then
	    assertThrows(InvalidLoanException.class, () -> loanService.repay(1L, 100.0, 1L));
	}

	@Test
	void testRepay_LoanNotBelongToUser() {
		// Given
		Loan loan = new Loan();
		loan.setId(1L);
		loan.setCustomer(new Member());
		loan.getCustomer().setId(2L); // Different user

		when(userService.isValidMember(1L)).thenReturn(true);
		when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

		// When & Then
		assertThrows(LoanNotBelongToUserException.class, () -> loanService.repay(1L, 100.0, 1L));
	}

	@Test
	void testRepay_CompletedLoan() {
		// Given
		Loan loan = new Loan();
		loan.setId(1L);
		loan.setState(StatusEnum.COMPLETED);
		loan.setCustomer(new Member());
		loan.getCustomer().setId(1L);

		when(userService.isValidMember(1L)).thenReturn(true);
		when(loanRepository.findById(1L)).thenReturn(Optional.of(loan));

		// When & Then
		assertThrows(LoanCompletedException.class, () -> loanService.repay(1L, 100.0, 1L));
	}

}
