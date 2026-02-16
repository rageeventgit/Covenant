package net.rageevent.covenant;

import net.rageevent.covenant.loader.BankLoader;

import java.util.UUID;

public interface BankService {

    Bank getBank(UUID bankId);
    Bank createBank();

    void deleteBank(UUID bankId);
    void updateBank(Bank bank);

    BankLoader getBankLoader();
}
