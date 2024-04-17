package com.aspire.loanbe.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class LoanCompletedException extends RuntimeException {

	public LoanCompletedException(String message) {
		super(message);
	}
}
