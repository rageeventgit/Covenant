package net.rageevent.covenant.loader;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import lombok.SneakyThrows;
import net.rageevent.covenant.Bank;
import net.rageevent.covenant.economy.SimpleBank;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

public class JsonBankLoader implements BankLoader {

    private final Path file;
    private final ObjectNode root;

    private final ObjectMapper jsonNodeMapper = new ObjectMapper();

    @SneakyThrows
    public JsonBankLoader(Path file) {
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

    }

    @Override
    public void delete(UUID bankId) {
    }
}
