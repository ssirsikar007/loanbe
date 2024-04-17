package com.aspire.loanbe.model;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;

@AllArgsConstructor
@Entity
public class Loan {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private double amount;
	private int term;
	private StatusEnum state;
	@ManyToOne
	private Member customer;
	private Date loanDate;

	@OneToMany(mappedBy = "loan")
	private List<ScheduledRepayment> scheduledRepayments;

	public Loan(Member customer, double amount, int term, StatusEnum state,
			List<ScheduledRepayment> scheduledRepayments, Date date) {
		this.customer = customer;
		this.amount = amount;
		this.term = term;
		this.state = state;
		this.scheduledRepayments = scheduledRepayments;
		this.loanDate = date;
	}

	public Loan(Member customer, double amount, int term, StatusEnum state, Date date) {
		this.customer = customer;
		this.amount = amount;
		this.term = term;
		this.state = state;
		this.loanDate = date;
	}

	public Loan() {
		super();
	}

	public Date getLoanDate() {
		return loanDate;
	}

	public void setLoanDate(Date loanDate) {
		this.loanDate = loanDate;
	}

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

	public Member getCustomer() {
		return customer;
	}

	public void setCustomer(Member customer) {
		this.customer = customer;
	}

	public List<ScheduledRepayment> getScheduledRepayments() {
		return scheduledRepayments;
	}

	public void setScheduledRepayments(List<ScheduledRepayment> scheduledRepayments) {
		this.scheduledRepayments = scheduledRepayments;
	}

}
