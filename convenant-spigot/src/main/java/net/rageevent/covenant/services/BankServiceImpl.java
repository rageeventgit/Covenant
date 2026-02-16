package net.rageevent.covenant.services;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.rageevent.covenant.Bank;
import net.rageevent.covenant.BankService;
import net.rageevent.covenant.economy.SimpleBank;
import net.rageevent.covenant.loader.BankLoader;
import net.rageevent.covenant.storage.BankCache;

import java.util.UUID;

@RequiredArgsConstructor
public class BankServiceImpl implements BankService {

    @Getter
    private final BankLoader bankLoader;
    private final BankCache cache;

    @Override
    public Bank getBank(UUID bankId) {
        Bank bank = cache.get(bankId);

        if(bank == null) {
            bank = bankLoader.load(bankId);
            cache.save(bank);
        }
        return bank;
    }

    @Override
    public Bank createBank() {
        return new SimpleBank(UUID.randomUUID());
    }

    @Override
    public void deleteBank(UUID bankId) {
        cache.delete(bankId);
        bankLoader.delete(bankId);
    }

    @Override
    public void updateBank(Bank bank) {
        cache.save(bank);
    }
}
