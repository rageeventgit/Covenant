package net.rageevent.covenant.result;

import net.rageevent.covenant.statement.TransactionStatement;

public sealed interface TransactionResult permits FailedTransactionResult, PassedTransactionResult {

    TransactionStatement statement();
    String message();

    default boolean failed(){
        return this instanceof FailedTransactionResult;
    }
    default boolean passed(){
        return this instanceof PassedTransactionResult;
    }
}
