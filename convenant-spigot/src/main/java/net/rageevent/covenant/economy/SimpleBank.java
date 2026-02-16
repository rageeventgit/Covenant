package net.rageevent.covenant.economy;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rageevent.covenant.Bank;
import net.rageevent.covenant.result.FailedTransactionResult;
import net.rageevent.covenant.result.PassedTransactionResult;
import net.rageevent.covenant.result.TransactionResult;
import net.rageevent.covenant.statement.TransactionStateType;
import net.rageevent.covenant.statement.TransactionStatement;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
public class SimpleBank implements Bank {
    private int balance;

    private final UUID bankId;
    private final List<TransactionStatement> statementHistory;

    public SimpleBank(UUID bankId) {
        this(bankId, new ArrayList<>());
    }

    @Override
    public TransactionResult addToBalance(int amount) {
        TransactionStatement addStatement = new TransactionStatement(TransactionStateType.ADD, balance, amount);
        TransactionResult result = new PassedTransactionResult(addStatement);

        statementHistory.add(result.statement());
        balance = result.statement().finalized();
        return result;
    }

    public TransactionResult removeFromBalance(int amount) {
        TransactionStatement removeStatement = new TransactionStatement(TransactionStateType.REMOVE, balance, amount);
        statementHistory.add(removeStatement);

        if (removeStatement.finalized() < 0) {
            balance = 0;
            return new FailedTransactionResult("Balance can't be negative. Set balance to 0!");
        }
        balance = removeStatement.finalized();
        return new PassedTransactionResult(removeStatement);
    }
}
