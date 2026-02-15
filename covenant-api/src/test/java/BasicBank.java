import lombok.Getter;
import net.rageevent.covenant.Bank;
import net.rageevent.covenant.result.FailedTransactionResult;
import net.rageevent.covenant.result.PassedTransactionResult;
import net.rageevent.covenant.result.TransactionResult;
import net.rageevent.covenant.statement.TransactionStateType;
import net.rageevent.covenant.statement.TransactionStatement;

import java.util.ArrayList;
import java.util.List;


public class BasicBank implements Bank {

    private final List<TransactionStatement> statements = new ArrayList<TransactionStatement>();
    @Getter
    private int balance;

    @Override
    public List<TransactionStatement> getStatementHistory() {
        return statements;
    }

    @Override
    public TransactionResult addToBalance(int amount) {
        TransactionStatement addStatement = new TransactionStatement(TransactionStateType.ADD, balance, amount);
        TransactionResult result = new PassedTransactionResult(addStatement);

        statements.add(result.statement());
        balance = result.statement().finalized();
        return result;
    }

    public TransactionResult removeFromBalance(int amount) {
        TransactionStatement removeStatement = new TransactionStatement(TransactionStateType.REMOVE, balance, amount);
        statements.add(removeStatement);

        if (removeStatement.finalized() < 0) {
            balance = 0;
            return new FailedTransactionResult("Balance can't be negative. Set balance to 0!");
        }
        balance = removeStatement.finalized();
        return new PassedTransactionResult(removeStatement);
    }
}
