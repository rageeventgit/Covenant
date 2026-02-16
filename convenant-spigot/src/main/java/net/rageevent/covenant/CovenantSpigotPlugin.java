package net.rageevent.covenant;

import net.rageevent.covenant.storage.BankLoader;
import net.rageevent.covenant.economy.loader.JsonBankLoader;
import net.rageevent.covenant.economy.services.BankServiceImpl;
import net.rageevent.covenant.storage.BankCache;
import net.rageevent.covenant.economy.storage.BankCacheImpl;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public final class CovenantSpigotPlugin extends JavaPlugin {


    private BankCache bankCache;
    private BankService bankService;

    @Override
    public void onEnable() {
        bankCache = new BankCacheImpl();
        BankLoader bankLoader = new JsonBankLoader(this, getDataPath().resolve("banks.json"));
        bankService = new BankServiceImpl(bankLoader, bankCache);
        getServer().getServicesManager().register(BankService.class, bankService, this, ServicePriority.Normal);
        //To save space I will probably just use the player's PDC, since this will not be used publicly.
    }

    @Override
    public void onDisable() {
        for (UUID bankId : bankCache.loaded()) {
            Bank cachedBank = bankCache.get(bankId);

            if (cachedBank != null)
                bankService.saveBank(cachedBank);

        }
    }
}
