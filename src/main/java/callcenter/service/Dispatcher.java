package callcenter.service;

import callcenter.model.Call;
import callcenter.model.Employee;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

@Data
public class Dispatcher implements Runnable {

    private ConcurrentLinkedDeque<Call> incomingCalls = new ConcurrentLinkedDeque<>();
    private final Logger log = LoggerFactory.getLogger(Dispatcher.class);
    private static Dispatcher dispatcher = new Dispatcher();
    ExecutorService executorEmployees = Executors.newFixedThreadPool(EmployeeService.getEmployeeList().size());

    private Dispatcher() {}

    public static Dispatcher getInstance() {
        return dispatcher;
    }

    public synchronized void dispathCall() {
        try {
            while (true) {
                if (!getIncomingCalls().isEmpty()) {
                    Employee employee = EmployeeService.getAvailableEmployee();

                    // si no hay ningun empleado disponible se seguira iterando hasta q haya uno disponible
                    if (employee != null) {
                        Call call = getIncomingCalls().poll();
                        try {
                            employee.assignCall(call);
                            executorEmployees.execute(employee);
                        } catch (Exception e) {
                            log.error(e.getMessage());
                            getIncomingCalls().addFirst(call);
                        }
                    }
                }

                TimeUnit.SECONDS.sleep(1);
            }
        } catch (InterruptedException ex) {
            log.error(ex.getMessage());
        }
    }

    @Override
    public void run() {
        synchronized(this) {
            dispathCall();
        }
    }
}
