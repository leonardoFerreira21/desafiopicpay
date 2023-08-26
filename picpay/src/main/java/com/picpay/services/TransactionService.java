package com.picpay.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.domain.user.User;
import com.picpay.dtos.TransactionDTO;
import com.picpay.repositories.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionRepository repository;
	
	public void createTransaction(TransactionDTO transaction) throws Exception {
		
		User sender = this.userService.findUserById(transaction.senderId());
		User reciver = this.userService.findUserById(transaction.reciverId());
		
		userService.validateTransaction(sender, transaction.value());
	}
	

}
