package callcenter.config;

import callcenter.service.Dispatcher;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DispatcherRunner implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        new Thread(Dispatcher.getInstance()).start();
    }
}
