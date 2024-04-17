package com.aspire.loanbe.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class ScheduledRepayment {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne
	private Loan loan;
	private Date repaymentDate;
	private double amount;
	private StatusEnum state;

	public ScheduledRepayment(Loan loan, Date repaymentDate, double amount, StatusEnum state) {
		super();
		this.loan = loan;
		this.repaymentDate = repaymentDate;
		this.amount = amount;
		this.state = state;
	}

	public ScheduledRepayment() {
		super();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Loan getLoan() {
		return loan;
	}

	public void setLoan(Loan loan) {
		this.loan = loan;
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
