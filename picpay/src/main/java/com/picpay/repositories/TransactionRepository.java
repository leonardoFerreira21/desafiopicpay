package com.picpay.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.picpay.domain.transaction.Transaction;
import com.picpay.domain.user.User;

public interface TransactionRepository extends JpaRepository<Transaction, Long>{

	
	

}
