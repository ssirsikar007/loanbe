package com.aspire.loanbe.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aspire.loanbe.model.Member;

@Repository
public interface UserRepository extends JpaRepository<Member, Long> {

}
