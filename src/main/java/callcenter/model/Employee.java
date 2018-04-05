package callcenter.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.TimeUnit;

@Data
public class Employee implements Runnable {

    private String name;
    private EmployeeType employeeType;
    private EmployeeState employeeState;
    private Call incomingCall;
    private ConcurrentLinkedDeque<Call> attendedCalls = new ConcurrentLinkedDeque<>();
    private final Logger log = LoggerFactory.getLogger(Employee.class);

    public Employee(String name, EmployeeType employeeType) {
        this.name = name;
        this.employeeType = employeeType;
        this.employeeState = EmployeeState.AVAILABLE;
    }

    /**
     * pone en cola la llamada para ser atendida cuanto antes
     *
     * @param call llamada a ser atendida
     */
    public synchronized void assignCall(Call call) {
        setIncomingCall(call);
        setEmployeeState(EmployeeState.BUSY);
    }

    @Override
    public void run() {
        synchronized(this) {
            try {
                if (getIncomingCall() != null) {
                    log.info("Start " + getName() + ", Call: " + getIncomingCall().getId() + " of " + getIncomingCall().getDuration() + " secs");

                    TimeUnit.SECONDS.sleep(7);

                    setEmployeeState(EmployeeState.AVAILABLE);
                    getAttendedCalls().add(getIncomingCall());

                    log.info("End " + getName() + ", Call: " + getIncomingCall().getId() + " of " + getIncomingCall().getDuration() + " secs");
                    setIncomingCall(null);
                }
            } catch (InterruptedException e) {
                log.error(e.getMessage());
            }
        }
    }
}
