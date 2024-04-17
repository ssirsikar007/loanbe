package com.aspire.loanbe.model;

import jakarta.persistence.Entity;

@Entity
public class Admin extends Member {

	public Admin(String username, boolean isAdmin) {
		super(username, isAdmin);
	}

}
