package com.example.transactionstarter.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.transactionstarter.entity.Transaction;
import com.example.transactionstarter.enums.TransactionStatus;
import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.InvalidStatusTransitionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import com.example.transactionstarter.repository.TransactionRepository;

@Service
public class TransactionService {
	private final TransactionRepository transactionRepository;
	
	public TransactionService(TransactionRepository transactionRepository) {
		this.transactionRepository = transactionRepository;
	}
	
	public Transaction createTransaction(Transaction transaction) {
		if (transactionRepository.existsById(transaction.getTransactionId())) {
			throw new DuplicateTransactionException(
			        "Transaction ID already exists: " + transaction.getTransactionId());
		}
		transaction.setStatus(TransactionStatus.PENDING);
		return transactionRepository.save(transaction);
		
	}
	public Transaction getTransaction(String transactionId) {
		
		return transactionRepository.findById(transactionId).orElseThrow(() ->
		new TransactionNotFoundException("Transction not found"));
	}
	public Transaction updateTransactionStatus(
		String transactionId , TransactionStatus newStatus) {
			Transaction transaction = transactionRepository.findById(transactionId)
					.orElseThrow(() ->
					new TransactionNotFoundException("Transaction not found"));
			
			TransactionStatus currentStatus = transaction.getStatus();
			
			if(currentStatus != TransactionStatus.PENDING) {
				throw new InvalidStatusTransitionException(
				        "Transaction status cannot be changed from " + currentStatus);
			}
			transaction.setStatus(newStatus);
			return transactionRepository.save(transaction);
			
	}
	  public List<Transaction> getCustomerTransactions(String customerId) {

	        return transactionRepository.findByCustomerId(customerId);
	    }

}
