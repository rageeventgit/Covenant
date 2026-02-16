package net.rageevent.covenant.storage;

import net.rageevent.covenant.Bank;

import java.util.UUID;

public interface BankCache {

    void save(Bank bank);
    void delete(UUID bankId);
    Bank get(UUID bankId);
}
