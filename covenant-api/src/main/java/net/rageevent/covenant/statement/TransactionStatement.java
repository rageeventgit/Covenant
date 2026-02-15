package net.rageevent.covenant.statement;

public record TransactionStatement(TransactionStateType type, long time, int current, int changed) {

    public TransactionStatement(TransactionStateType type, int current, int changed) {
        this(type, System.currentTimeMillis(), current, changed);
    }

    public int finalized(){
        return type.calculate(current, changed);
    }
}

