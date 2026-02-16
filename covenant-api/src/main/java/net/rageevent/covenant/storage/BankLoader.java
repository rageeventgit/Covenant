package net.rageevent.covenant.storage;

import net.rageevent.covenant.Bank;

import java.util.UUID;

public interface BankLoader {

    Bank load(UUID bankId);
    void save(Bank bank);
    void delete(UUID bankId);

}
