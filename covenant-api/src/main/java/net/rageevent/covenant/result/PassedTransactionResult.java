package net.rageevent.covenant.result;

import net.rageevent.covenant.statement.TransactionStatement;

public record PassedTransactionResult(TransactionStatement statement, String message) implements TransactionResult {

    public PassedTransactionResult(TransactionStatement amount) {
        this(amount, "Transaction passed successfully.");
    }

}
