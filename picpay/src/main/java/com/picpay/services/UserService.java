package com.picpay.services;

import java.math.BigDecimal;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.picpay.domain.user.User;
import com.picpay.domain.user.UserType;
import com.picpay.repositories.UserRepository;



@Service

public class UserService {
	
	@Autowired
	private UserRepository repository;
	
	
	public void validateTransaction(User sender, BigDecimal amount) throws Exception {
		
		if(sender.getUserType() == UserType.MERCHANT ) {
			
			throw new Exception ("usuário do tipo logista não está autorizado a fazer esse tipo de transação");
		}
		
		if(sender.getBalance().compareTo(amount) < 0 ) {
			throw new Exception ("saldo insuficiente!");
		}
	
	}
	
	public User findUserById(Long id) throws Exception {
		return this.repository.findUserById(id).orElseThrow(() -> new Exception("Usuário não encontrado"));
	}
	
	public void saveUser(User user) {
		this.repository.save(user);
	}
	
	
	
	
	
	
}
