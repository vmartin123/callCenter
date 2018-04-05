package callcenter;

import callcenter.model.Call;
import callcenter.model.Employee;
import callcenter.model.EmployeeState;
import callcenter.model.EmployeeType;
import callcenter.service.EmployeeService;
import org.junit.Test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class EmployeeTests {

    @Test
    public void testEmployeeCreation() {
        Employee employee = EmployeeService.buildOperator("Victor");

        assertNotNull(employee);
        assertEquals("Victor", employee.getName());
        assertEquals(EmployeeType.OPERATOR, employee.getEmployeeType());
        assertEquals(EmployeeState.AVAILABLE, employee.getEmployeeState());
    }

    @Test
    public void testEmployeeInvalidCreation() {
        Employee employee = new Employee(null, null);
        assertNull(employee.getName());
        assertNull(employee.getEmployeeType());
    }

    @Test
    public void testEmployeeAnswerACall() throws InterruptedException {
        Employee employee = EmployeeService.buildOperator("Victor");
        employee.assignCall(new Call(1, 6));

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(employee);

        executorService.awaitTermination(8, TimeUnit.SECONDS);
        assertEquals(1, employee.getAttendedCalls().size());
    }

    @Test
    public void testEmployeeStateIsBusyInACall() throws InterruptedException {
        Employee employee = EmployeeService.buildOperator("Victor");

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        executorService.execute(employee);

        assertEquals(EmployeeState.AVAILABLE, employee.getEmployeeState());

        employee.assignCall(new Call(2, 5));
        TimeUnit.SECONDS.sleep(4);
        assertEquals(EmployeeState.BUSY, employee.getEmployeeState());

        executorService.awaitTermination(6, TimeUnit.SECONDS);
        assertEquals(1, employee.getAttendedCalls().size());
    }
}
