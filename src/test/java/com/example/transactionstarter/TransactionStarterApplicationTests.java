package com.example.transactionstarter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.transactionstarter.entity.Transaction;
import com.example.transactionstarter.enums.TransactionStatus;
import com.example.transactionstarter.enums.TransactionType;
import com.example.transactionstarter.exception.DuplicateTransactionException;
import com.example.transactionstarter.exception.TransactionNotFoundException;
import com.example.transactionstarter.repository.TransactionRepository;
import com.example.transactionstarter.service.TransactionService;

@ExtendWith(MockitoExtension.class)
class TransactionServiceTest {

    @Mock
    private TransactionRepository transactionRepository;

    @InjectMocks
    private TransactionService transactionService;


    // 1. Create transaction successfully
    @Test
    void createTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TXN100",
                "CUST100",
                new BigDecimal("500.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(transactionRepository.existsById("TXN100"))
                .thenReturn(false);

        when(transactionRepository.save(transaction))
                .thenReturn(transaction);

        Transaction result =
                transactionService.createTransaction(transaction);

        assertNotNull(result);
        assertEquals("TXN100", result.getTransactionId());
        assertEquals("CUST100", result.getCustomerId());
        assertEquals(new BigDecimal("500.00"), result.getAmount());
        assertEquals(TransactionStatus.PENDING, result.getStatus());
    }


    // 2. Duplicate transaction ID
    @Test
    void duplicateTransactionIdThrowsException() {

        Transaction transaction = new Transaction(
                "TXN100",
                "CUST100",
                new BigDecimal("500.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(transactionRepository.existsById("TXN100"))
                .thenReturn(true);

        assertThrows(
                DuplicateTransactionException.class,
                () -> transactionService.createTransaction(transaction)
        );
    }


    // 3. Get transaction successfully
    @Test
    void getTransactionSuccessfully() {

        Transaction transaction = new Transaction(
                "TXN100",
                "CUST100",
                new BigDecimal("500.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(transactionRepository.findById("TXN100"))
                .thenReturn(java.util.Optional.of(transaction));

        Transaction result =
                transactionService.getTransaction("TXN100");

        assertNotNull(result);
        assertEquals("TXN100", result.getTransactionId());
        assertEquals("CUST100", result.getCustomerId());
    }


    // 4. Transaction not found
    @Test
    void getTransactionNotFoundThrowsException() {

        when(transactionRepository.findById("TXN999"))
                .thenReturn(java.util.Optional.empty());

        assertThrows(
                TransactionNotFoundException.class,
                () -> transactionService.getTransaction("TXN999")
        );
    }


    // 5. Update transaction status successfully
    @Test
    void updateTransactionStatusSuccessfully() {

        Transaction transaction = new Transaction(
                "TXN100",
                "CUST100",
                new BigDecimal("500.00"),
                "INR",
                TransactionType.PAYMENT,
                TransactionStatus.PENDING
        );

        when(transactionRepository.findById("TXN100"))
                .thenReturn(java.util.Optional.of(transaction));

        when(transactionRepository.save(transaction))
                .thenReturn(transaction);

        Transaction result =
                transactionService.updateTransactionStatus(
                        "TXN100",
                        TransactionStatus.COMPLETED
                );

        assertNotNull(result);
        assertEquals(
                TransactionStatus.COMPLETED,
                result.getStatus()
        );
    }
    
}