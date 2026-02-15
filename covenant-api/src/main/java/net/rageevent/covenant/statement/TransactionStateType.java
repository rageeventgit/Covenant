package net.rageevent.covenant.statement;

public enum TransactionStateType {
    ADD {
        @Override
        public int calculate(int balance, int changed) {
            return balance + changed;
        }
    },
    REMOVE {
        @Override
        public int calculate(int balance, int changed) {
            return balance - changed;
        }
    };


    public int calculate(int balance, int changed) {
        return 0;
    }
}
