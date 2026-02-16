package net.rageevent.covenant;

import net.rageevent.covenant.loader.BankLoader;
import net.rageevent.covenant.loader.JsonBankLoader;
import net.rageevent.covenant.services.BankServiceImpl;
import net.rageevent.covenant.storage.BankCache;
import net.rageevent.covenant.storage.BankCacheImpl;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;

public final class CovenantSpigotPlugin extends JavaPlugin {


    @Override
    public void onEnable() {
        BankCache bankCache = new BankCacheImpl();
        BankLoader bankLoader = new JsonBankLoader(getDataPath().resolve("banks.json"));
        BankService bankService = new BankServiceImpl(bankLoader, bankCache);

        getServer().getServicesManager().register(BankService.class, bankService, this, ServicePriority.Normal);
    }

    @Override
    public void onDisable() {
    }
}
