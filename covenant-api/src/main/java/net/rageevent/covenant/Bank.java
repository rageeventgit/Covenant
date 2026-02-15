package net.rageevent.covenant;

import net.rageevent.covenant.result.TransactionResult;
import net.rageevent.covenant.statement.TransactionStatement;

import java.util.List;

public interface Bank {

    List<TransactionStatement> getStatementHistory();

    TransactionResult addToBalance(int amount);
    TransactionResult removeFromBalance(int amount);
    int getBalance();
}
