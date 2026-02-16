package net.rageevent.covenant;

import net.rageevent.covenant.result.TransactionResult;
import net.rageevent.covenant.statement.TransactionStatement;

import java.util.List;
import java.util.UUID;

public interface Bank {

    UUID getBankId();

    List<TransactionStatement> getStatementHistory();

    TransactionResult addToBalance(int amount);
    TransactionResult removeFromBalance(int amount);
    int getBalance();
}
