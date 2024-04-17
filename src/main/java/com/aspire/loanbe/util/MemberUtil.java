package com.aspire.loanbe.util;

import java.util.Optional;

import com.aspire.loanbe.exception.CustomerNotFoundException;
import com.aspire.loanbe.exception.LoanNotFoundException;
import com.aspire.loanbe.exception.NotAdminException;
import com.aspire.loanbe.message.ErrorMessageConstants;
import com.aspire.loanbe.model.Loan;
import com.aspire.loanbe.model.Member;

public class MemberUtil {

	private MemberUtil() {

	}

	private static MemberUtil instance = null;

	public static MemberUtil getInstance() {
		if (instance == null) {
			synchronized (MemberUtil.class) {
				if (instance == null) {
					instance = new MemberUtil();
				}
			}
		}
		return instance;
	}

	public boolean isAdmin(Member member) {
		return member.isAdmin();
	}

	public boolean isLoadValidAndMemberAdmin(Optional<Loan> loan, Optional<Member> member)
			throws NotAdminException, LoanNotFoundException {
		if (!member.isPresent() || !member.get().isAdmin()) {
			throw new NotAdminException(ErrorMessageConstants.USER_NOT_ADMIN);
		}
		if (loan.isPresent() && member.get().isAdmin()) {
			return true;
		} else {
			throw new LoanNotFoundException(ErrorMessageConstants.INVALID_LOAN);
		}
	}
}
