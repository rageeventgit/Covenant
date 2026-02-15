package net.rageevent.covenant.result;

import net.rageevent.covenant.statement.TransactionStatement;

public record FailedTransactionResult(String message) implements TransactionResult {

    @Override
    public TransactionStatement statement() {
        return null;
    }
}
