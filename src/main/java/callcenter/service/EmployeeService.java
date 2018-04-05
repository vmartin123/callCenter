package callcenter.service;

import callcenter.model.Employee;
import callcenter.model.EmployeeState;
import callcenter.model.EmployeeType;
import lombok.Data;

import java.util.*;
import java.util.stream.Collectors;

@Data
public class EmployeeService {

    private static List<Employee> employeeList = new ArrayList<>();
    private static final int MAX_CALLS_ALLOWED = 10;

    public static synchronized Employee getAvailableEmployee() {
        int operatorsBusy = getEmployeeList().stream().filter(e -> e.getEmployeeState() == EmployeeState.BUSY).collect(Collectors.toList()).size();

        // valida que no hayan mas de 10 llamadas activas al no asignarle usuarios
        // esta validacion persistira hasta que no se sobrepase el limite de las 10 permitidas
        if (operatorsBusy < MAX_CALLS_ALLOWED) {
            List<Employee> availableEmployees = getEmployeeList().stream().filter(e -> e.getEmployeeState() == EmployeeState.AVAILABLE).collect(Collectors.toList());

            Optional<Employee> employee = availableEmployees.stream().filter(e -> e.getEmployeeType() == EmployeeType.OPERATOR).findAny();
            if (!employee.isPresent()) {
                employee = availableEmployees.stream().filter(e -> e.getEmployeeType() == EmployeeType.SUPERVISOR).findAny();

                if (!employee.isPresent()) {
                    employee = availableEmployees.stream().filter(e -> e.getEmployeeType() == EmployeeType.DIRECTOR).findAny();

                    if (!employee.isPresent()) {
                        return null;
                    }
                }
            }

            return employee.get();
        }

        return null;
    }

    public static List<Employee> getEmployeeList() {
        if (employeeList.size() == 0) {
            setEmployeeList(buildEmployeeList());
        }

        return employeeList;
    }

    public static void setEmployeeList(List<Employee> employeeList) {
        EmployeeService.employeeList = employeeList;
    }

    private static List<Employee> buildEmployeeList() {
        return Arrays.asList(buildOperator("Victor"),
                buildOperator("Dubraska"),
                buildOperator("Pedro"),
                buildOperator("Juan"),
                buildOperator("Simon"),
                buildOperator("Andres"),
                buildOperator("Ana"),

                buildSupervisor("Jose"),
                buildSupervisor("Luis"),

                buildDirector("Omar"));
    }

    public static Employee buildOperator(String name) {
        return new Employee(name,EmployeeType.OPERATOR);
    }

    public static Employee buildSupervisor(String name) {
        return new Employee(name,EmployeeType.SUPERVISOR);
    }

    public static Employee buildDirector(String name) {
        return new Employee(name,EmployeeType.DIRECTOR);
    }
}
