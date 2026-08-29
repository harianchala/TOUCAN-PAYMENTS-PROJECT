package com.example.transactionstarter.entity;

import java.math.BigDecimal;

import com.example.transactionstarter.enums.TransactionStatus;
import com.example.transactionstarter.enums.TransactionType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

@Entity

public class Transaction {
	
	

	@Id
	@NotBlank
	private	String transactionId;
	@NotBlank
	private	String customerId;
	@NotNull
	@Positive
	private	BigDecimal amount;
	@NotBlank
	private String currency;
	
	@NotNull
	 @Enumerated(EnumType.STRING)
	    private TransactionType transactionType;
	 @NotNull
	    @Enumerated(EnumType.STRING)
	    private TransactionStatus status;
	    
	    public Transaction() {
	    	
	    }
	    
	    public Transaction(String transactionId, String customerId, BigDecimal amount, String currency,
				TransactionType transactionType, TransactionStatus status) {
			super();
			this.transactionId = transactionId;
			this.customerId = customerId;
			this.amount = amount;
			this.currency = currency;
			this.transactionType = transactionType;
			this.status = status;
		}

		public String getTransactionId() {
			return transactionId;
		}

		public void setTransactionId(String transactionId) {
			this.transactionId = transactionId;
		}

		public String getCustomerId() {
			return customerId;
		}

		public void setCustomerId(String customerId) {
			this.customerId = customerId;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public String getCurrency() {
			return currency;
		}

		public void setCurrency(String currency) {
			this.currency = currency;
		}

		public TransactionType getTransactionType() {
			return transactionType;
		}

		public void setTransactionType(TransactionType transactionType) {
			this.transactionType = transactionType;
		}

		public TransactionStatus getStatus() {
			return status;
		}

		public void setStatus(TransactionStatus status) {
			this.status = status;
		}
	    
	    
	

}
