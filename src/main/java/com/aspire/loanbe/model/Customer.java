package com.aspire.loanbe.model;

import jakarta.persistence.Entity;

@Entity
public class Customer extends Member {

	public Customer(String username, boolean isAdmin) {
		super(username, isAdmin);

	}

}
