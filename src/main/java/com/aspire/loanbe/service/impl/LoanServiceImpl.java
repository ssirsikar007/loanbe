package com.aspire.loanbe.service.impl;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.aspire.loanbe.dto.LoanRequest;
import com.aspire.loanbe.dto.UserLoanResponse;
import com.aspire.loanbe.exception.CustomerNotFoundException;
import com.aspire.loanbe.exception.InsufficientRepaymentAmountException;
import com.aspire.loanbe.exception.InvalidLoanException;
import com.aspire.loanbe.exception.LoanCompletedException;
import com.aspire.loanbe.exception.LoanNotBelongToUserException;
import com.aspire.loanbe.exception.NotAdminException;
import com.aspire.loanbe.message.ErrorMessageConstants;
import com.aspire.loanbe.message.SuccessMessageConstants;
import com.aspire.loanbe.model.Loan;
import com.aspire.loanbe.model.Member;
import com.aspire.loanbe.model.ScheduledRepayment;
import com.aspire.loanbe.model.StatusEnum;
import com.aspire.loanbe.repository.LoanRepository;
import com.aspire.loanbe.repository.ScheduledRepaymentRepository;
import com.aspire.loanbe.service.ILoanService;
import com.aspire.loanbe.service.UserService;
import com.aspire.loanbe.util.LoanUtil;
import com.aspire.loanbe.util.MemberUtil;

import jakarta.transaction.Transactional;

@Service
public class LoanServiceImpl implements ILoanService {

	private final LoanRepository loanRepository;
	private final ScheduledRepaymentRepository scheduledRepaymentRepository;
	private final UserService userService;

	public LoanServiceImpl(LoanRepository loanRepository, ScheduledRepaymentRepository scheduledRepaymentRepository,
			UserService userService) {
		this.loanRepository = loanRepository;
		this.scheduledRepaymentRepository = scheduledRepaymentRepository;
		this.userService = userService;
	}

	@Transactional
	@Override
	public void createLoan(LoanRequest loanRequest, Long customerId) {
		Optional<Member> OCustomer = userService.getCustomerById(customerId);
		if (!OCustomer.isPresent()) {
			throw new CustomerNotFoundException(ErrorMessageConstants.INVALID_CUSTOMER);
		}
		Loan loan = new Loan(OCustomer.get(), loanRequest.getAmount(), loanRequest.getTerm(), StatusEnum.PENDING,
				loanRequest.getDate());
		// Calculate scheduled repayments
		List<ScheduledRepayment> scheduledRepayments = LoanUtil.getInstance().calculateScheduledRepayments(loan);

		for (ScheduledRepayment repayment : scheduledRepayments) {
			scheduledRepaymentRepository.save(repayment);
		}

		loan.setScheduledRepayments(scheduledRepayments);

		loanRepository.save(loan);

	}

	@Override
	public List<UserLoanResponse> getLoans(Long memberId) {
		if (!userService.isValidMember(memberId)) {
			throw new CustomerNotFoundException(ErrorMessageConstants.INVALID_CUSTOMER);
		}
		List<Loan> loans = loanRepository.findByCustomerId(memberId);
		List<UserLoanResponse> userLoans = LoanUtil.getInstance().mapToUserLoanResponse(loans);
		return userLoans;
	}

	@Override
	public Loan aproveLoan(long loanId, long adminId) throws CustomerNotFoundException, NotAdminException {
		Optional<Member> admin = userService.getCustomerById(adminId);
		Optional<Loan> oLoan = loanRepository.findById(loanId);
		Loan loan = null;
		if (MemberUtil.getInstance().isLoadValidAndMemberAdmin(oLoan, admin)) {
			loan = oLoan.get();
			loan.setState(StatusEnum.APPROVED);
			loan = loanRepository.save(loan);
			return loan;
		}
		return loan;
	}

	@Override
	public void repay(long loanId, double amount, long userId) {
		if (!userService.isValidMember(userId)) {
			throw new CustomerNotFoundException(ErrorMessageConstants.INVALID_CUSTOMER);
		}
		Optional<Loan> oLoan = loanRepository.findById(loanId);
		if (!oLoan.isPresent()) {
			throw new InvalidLoanException(ErrorMessageConstants.INVALID_LOAN);
		}
		Loan loan = oLoan.get();
		if (loan.getCustomer().getId() != userId) {
			throw new LoanNotBelongToUserException(ErrorMessageConstants.LOAN_DOES_NOT_BELONG_TO_USER);
		}
		if (loan.getState().equals(StatusEnum.COMPLETED)) {
			throw new LoanCompletedException(SuccessMessageConstants.LOAN_PAYMENT_COMPLETED);
		}
		if (loan.getState().equals(StatusEnum.PENDING)) {
			throw new LoanCompletedException(SuccessMessageConstants.LOAN_NOT_APPROVED);
		}
		List<ScheduledRepayment> scheduledRepayments = scheduledRepaymentRepository.getByLoanId(loanId);
		List<ScheduledRepayment> sortedList = scheduledRepayments.stream()
				.filter(scheduledPayment -> StatusEnum.PENDING.equals(scheduledPayment.getState()))
				.sorted(Comparator.comparing(ScheduledRepayment::getRepaymentDate)).collect(Collectors.toList());
		if (sortedList.size() > 0) {
			ScheduledRepayment scheduledRepayment = sortedList.get(0);
			if (amount >= scheduledRepayment.getAmount()) {
				scheduledRepayment.setState(StatusEnum.APPROVED);
				scheduledRepaymentRepository.save(scheduledRepayment);
				if (sortedList.size() == 1) {
					loan.setState(StatusEnum.COMPLETED);
					loanRepository.save(loan);
				}
			} else {
				throw new InsufficientRepaymentAmountException(ErrorMessageConstants.REPAY_AMOUNT_LESSER);
			}
		}

	}

}
