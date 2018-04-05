package callcenter;

import callcenter.model.Employee;
import callcenter.model.EmployeeState;
import callcenter.model.EmployeeType;
import callcenter.service.EmployeeService;
import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class EmployeeServiceTests {

    @Test
    public void testAssignToOperator() {
        Employee operator = EmployeeService.buildOperator("Victor");
        Employee supervisor = EmployeeService.buildSupervisor("Dubraska");
        Employee director = EmployeeService.buildDirector("Juan");

        EmployeeService.setEmployeeList(Arrays.asList(operator, supervisor, director));

        Employee employee = EmployeeService.getAvailableEmployee();

        assertNotNull(employee);
        assertEquals(EmployeeType.OPERATOR, employee.getEmployeeType());
    }

    @Test
    public void testAssignToSupervisor() {
        Employee operator = mock(Employee.class);
        when(operator.getEmployeeState()).thenReturn(EmployeeState.BUSY);

        Employee supervisor = EmployeeService.buildSupervisor("Dubraska");
        Employee director = EmployeeService.buildDirector("Juan");

        EmployeeService.setEmployeeList(Arrays.asList(supervisor, director));

        Employee employee = EmployeeService.getAvailableEmployee();

        assertNotNull(employee);
        assertEquals(EmployeeType.SUPERVISOR, employee.getEmployeeType());
    }

    @Test
    public void testAssignToDirector() {
        Employee operator = mockBusyEmployee(EmployeeType.OPERATOR);
        Employee supervisor = mockBusyEmployee(EmployeeType.SUPERVISOR);
        Employee director = EmployeeService.buildDirector("Victor");

        EmployeeService.setEmployeeList(Arrays.asList(operator, supervisor, director));

        Employee employee = EmployeeService.getAvailableEmployee();

        assertNotNull(employee);
        assertEquals(EmployeeType.DIRECTOR, employee.getEmployeeType());
    }

    private static Employee mockBusyEmployee(EmployeeType employeeType) {
        Employee employee = mock(Employee.class);
        when(employee.getEmployeeType()).thenReturn(employeeType);
        when(employee.getEmployeeState()).thenReturn(EmployeeState.BUSY);
        return employee;
    }

    @Test
    public void testAssignToNone() {
        Employee operator = mockBusyEmployee(EmployeeType.OPERATOR);
        Employee supervisor = mockBusyEmployee(EmployeeType.SUPERVISOR);
        Employee director = mockBusyEmployee(EmployeeType.DIRECTOR);

        EmployeeService.setEmployeeList(Arrays.asList(operator, supervisor, director));

        Employee employee = EmployeeService.getAvailableEmployee();

        assertNull(employee);
    }
}
