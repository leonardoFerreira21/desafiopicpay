package com.picpay.services;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAccessor;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.picpay.domain.transaction.Transaction;
import com.picpay.domain.user.User;
import com.picpay.dtos.TransactionDTO;
import com.picpay.repositories.TransactionRepository;

@Service
public class TransactionService {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private TransactionRepository repository;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void createTransaction(TransactionDTO transaction) throws Exception {
		
		User sender = this.userService.findUserById(transaction.senderId());
		User reciver = this.userService.findUserById(transaction.reciverId());
		
		userService.validateTransaction(sender, transaction.value());
		
		boolean isAuthorized = this.authorizeTransaction(sender, transaction.value());
		
		if(!isAuthorized) {
			throw new Exception("Transacão não autorizada");
		}
		
		Transaction newtransaction = new Transaction();
		newtransaction.setAmount(transaction.value());
		newtransaction.setSender(sender);
		newtransaction.setReciver(reciver);
		newtransaction.setTimestamp(LocalDateTime.now());
		
		sender.setBalance(sender.getBalance().subtract(transaction.value()));
		reciver.setBalance(sender.getBalance().add(transaction.value()));
		
		
		this.repository.save(newtransaction);
		this.userService.saveUser(sender);
		this.userService.saveUser(reciver);
	}
	
	
	public boolean authorizeTransaction(User sender, BigDecimal value) {
		
		ResponseEntity<Map> authorizationResponse = restTemplate.getForEntity("https://run.mocky.io/v3/8fafdd68-a090-496f-8c9a-3442cf30dae6", Map.class);
		
		if(authorizationResponse.getStatusCode() == HttpStatus.OK) {
			
			String message = (String) authorizationResponse.getBody().get("message");
			return "Autorizado".equalsIgnoreCase(message);
		}else return false;
		
		
	}
	 

}
