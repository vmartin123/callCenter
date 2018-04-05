package callcenter;

import callcenter.model.Call;
import org.junit.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.*;

public class CallTests {

    private final int MIN_CALL_DURATION = 5;
    private final int MAX_CALL_DURATION = 10;

    @Test
    public void testRandomCallWithValidParameters() {
        Call call = new Call(1, ThreadLocalRandom.current().nextInt(MIN_CALL_DURATION, MAX_CALL_DURATION));

        assertNotNull(call);
        assertTrue(call.getDuration() >= MIN_CALL_DURATION);
        assertTrue(call.getDuration() <= MAX_CALL_DURATION);
    }

    @Test
    public void testRandomCallWithInvalidParameters() {
        Call call = new Call();
        assertEquals(0,call.getId());
        assertEquals(0,call.getDuration());

        call = new Call(1, ThreadLocalRandom.current().nextInt(MIN_CALL_DURATION, MAX_CALL_DURATION));
        assertFalse(call.getDuration() >= 11);
        assertFalse(call.getDuration() <= 4);
    }
}
