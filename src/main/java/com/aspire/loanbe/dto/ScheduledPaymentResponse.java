package com.aspire.loanbe.dto;

import java.util.Date;

import com.aspire.loanbe.model.StatusEnum;

public class ScheduledPaymentResponse {
	private Long id;
	private Date repaymentDate;
	private double amount;
	private StatusEnum state;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getRepaymentDate() {
		return repaymentDate;
	}

	public void setRepaymentDate(Date repaymentDate) {
		this.repaymentDate = repaymentDate;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public StatusEnum getState() {
		return state;
	}

	public void setState(StatusEnum state) {
		this.state = state;
	}

}
