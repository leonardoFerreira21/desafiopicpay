package com.picpay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.picpay.domain.transaction.Transaction;
import com.picpay.domain.user.User;
import com.picpay.dtos.TransactionDTO;
import com.picpay.dtos.UserDTO;
import com.picpay.services.TransactionService;


@RestController
@RequestMapping("/transaction")


public class TransactionController {
	
	@Autowired
	private TransactionService transactionService;
	
	
	@PostMapping
	public ResponseEntity<Transaction> createUser(@RequestBody TransactionDTO transaction) throws Exception{
		
		Transaction newTransaction = this.transactionService.createTransaction(transaction);
		return new ResponseEntity<>(newTransaction, HttpStatus.CREATED);
		
	} 
	
	/*
	@GetMapping
	public ResponseEntity<List<Transaction>> getAllUsers(){
		List<Transaction> transaction = this.transactionService.getAllTransaction();

		return new ResponseEntity<>(transaction, HttpStatus.OK);
	}
	*/
	


}
