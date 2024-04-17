package com.aspire.loanbe.dto;

import java.util.Date;
import java.util.List;

import com.aspire.loanbe.model.StatusEnum;

public class UserLoanResponse {
	private Long id;

	private double amount;
	private int term;
	private StatusEnum state;
	private Date loanDate;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public int getTerm() {
		return term;
	}

	public void setTerm(int term) {
		this.term = term;
	}

	public StatusEnum getState() {
		return state;
	}

	public void setState(StatusEnum state) {
		this.state = state;
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

	public UserLoanResponse(Long id, double amount, int term, StatusEnum state, Date loanDate) {
		super();
		this.id = id;
		this.amount = amount;
		this.term = term;
		this.state = state;
		this.loanDate = loanDate;

	}

	public UserLoanResponse() {
		super();
	}

}
