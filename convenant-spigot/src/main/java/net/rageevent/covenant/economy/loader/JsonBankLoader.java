package net.rageevent.covenant.economy.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.rageevent.covenant.Bank;
import net.rageevent.covenant.economy.SimpleBank;
import net.rageevent.covenant.loader.BankLoader;
import org.bukkit.plugin.Plugin;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

public class JsonBankLoader implements BankLoader {

    private final Plugin plugin;
    private final Path file;
    private final ObjectNode root;

    private final ObjectMapper jsonNodeMapper = new ObjectMapper();

    @SneakyThrows
    public JsonBankLoader(Plugin plugin, Path file) {
        this.plugin = plugin;
        this.file = file;

        if (!Files.exists(file)) {
            Files.createFile(file);

            jsonNodeMapper.writeValue(file.toFile(), jsonNodeMapper.createObjectNode());
        }
        this.root = (ObjectNode) jsonNodeMapper.readTree(file.toFile());
    }

    @Override
    public Bank load(UUID bankId) {
        JsonNode bankNode = root.get(bankId.toString());

        if(bankNode == null) return null;

        //For right now it'll have to do.
        return jsonNodeMapper.convertValue(bankNode, SimpleBank.class);
    }

    @Override
    public void save(Bank bank) {
        JsonNode bankNode = jsonNodeMapper.valueToTree(bank);
        root.set(bank.getBankId().toString(),bankNode);
        try {
            atomicWriteTmp();
        } catch (IOException e) {
            plugin.getComponentLogger().error(Component.text("Failed to save bank!"), e);
        }
    }

    @Override
    public void delete(UUID bankId) {
        root.remove(bankId.toString());
        try {
            atomicWriteTmp();
        } catch (IOException e) {
            plugin.getComponentLogger().error(Component.text("Failed to delete bank!"), e);
        }
    }

    private void atomicWriteTmp() throws IOException {
        Path parent = file.getParent();
        if (parent != null) Files.createDirectories(parent);

        Path tmp = file.resolveSibling(file.getFileName() + ".tmp");
        try (OutputStream out = Files.newOutputStream(tmp)) {
            jsonNodeMapper.writerWithDefaultPrettyPrinter().writeValue(out, root);
        }
        Files.move(tmp, file,
                StandardCopyOption.REPLACE_EXISTING,
                StandardCopyOption.ATOMIC_MOVE);
    }
}
