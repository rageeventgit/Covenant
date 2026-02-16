package net.rageevent.covenant.storage;

import net.rageevent.covenant.Bank;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class BankCacheImpl implements BankCache {
    private final ConcurrentMap<UUID, Bank> cache = new ConcurrentHashMap<>();

    @Override
    public void save(Bank bank) {
        cache.put(bank.getBankId(), bank);
    }

    @Override
    public void delete(UUID bankId) {
        cache.remove(bankId);
    }

    @Override
    public Bank get(UUID bankId) {
        return cache.get(bankId);
    }

}
