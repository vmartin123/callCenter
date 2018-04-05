package callcenter;

import callcenter.model.Call;
import callcenter.service.Dispatcher;
import callcenter.service.EmployeeService;
import lombok.Data;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

@RunWith(SpringRunner.class)
@SpringBootTest
@Data
public class DispatcherTests {

	private final int TOTAL_CALLS_RIGHT = 10;
	private final int TOTAL_CALLS_WRONG = 2;
	private final int MIN_CALL_DURATION = 5;
	private final int MAX_CALL_DURATION = 10;
	List<Call> callsList = buildTestCalls();

	@Test
	public void testDispatchCallsToEmployeesAreCorrect() throws InterruptedException {
		EmployeeService.getEmployeeList().stream().forEach(employee -> employee.getAttendedCalls().clear());
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(Dispatcher.getInstance());

		getCallsList().forEach(call -> {
			Dispatcher.getInstance().getIncomingCalls().add(call);
		});

		executorService.awaitTermination(MAX_CALL_DURATION*2, TimeUnit.SECONDS);
		assertEquals(TOTAL_CALLS_RIGHT, EmployeeService.getEmployeeList().stream().mapToInt(employee -> employee.getAttendedCalls().size()).sum());
	}

	@Test
	public void testDispatchCallsToEmployeesAreInCorrect() throws InterruptedException {
		EmployeeService.getEmployeeList().stream().forEach(employee -> employee.getAttendedCalls().clear());
		ExecutorService executorService = Executors.newSingleThreadExecutor();
		executorService.execute(Dispatcher.getInstance());

		getCallsList().forEach(call -> {
			Dispatcher.getInstance().getIncomingCalls().add(call);
		});

		executorService.awaitTermination(MAX_CALL_DURATION*2, TimeUnit.SECONDS);
		assertNotEquals(TOTAL_CALLS_WRONG, EmployeeService.getEmployeeList().stream().mapToInt(employee -> employee.getAttendedCalls().size()).sum());
	}

	public List<Call> buildTestCalls() {
		List<Call> callList = new ArrayList<>();

		for (int i=1; i<=10; i++) {
			Call call = new Call(i, ThreadLocalRandom.current().nextInt(MIN_CALL_DURATION, MAX_CALL_DURATION));
			callList.add(call);
		}

		return callList;
	}
}
