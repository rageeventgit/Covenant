import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import net.rageevent.covenant.Bank;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class Main {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

        Bank bank = new BasicBank();

        scheduler.schedule(() -> {
            jsonPrint(bank.addToBalance(10));
            System.out.println("Balance: " + bank.getBalance());
        }, 5, TimeUnit.SECONDS);

        printHistory(scheduler, bank, 7);

        scheduler.schedule(() -> {
            jsonPrint(bank.removeFromBalance(5));
            System.out.println("Balance: " + bank.getBalance());
        }, 10, TimeUnit.SECONDS);

        printHistory(scheduler, bank, 12);

        scheduler.schedule(() -> {
            jsonPrint(bank.removeFromBalance(2));
            System.out.println("Balance: " + bank.getBalance());
        }, 15, TimeUnit.SECONDS);

        printHistory(scheduler, bank, 17);

    }

    @SneakyThrows
    private static void jsonPrint(Object a) {
        ObjectMapper mapper = new ObjectMapper();
        System.out.println(mapper.writeValueAsString(a));
    }

    //Too damn lazy to copy and paste.
    private static void printHistory(ScheduledExecutorService scheduler, Bank bank, int delay) {
        scheduler.schedule(() -> {
            jsonPrint(bank.getStatementHistory());
        }, delay, TimeUnit.SECONDS);
    }
}
