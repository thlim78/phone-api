package au.com.belong.domain.enums;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class StatusTest {
    @Test
    public void returnExpectedEnumValues() {
        Assertions.assertEquals(Status.valueOf("ACTIVATED"), Status.ACTIVATED);
        Assertions.assertEquals(Status.valueOf("DEACTIVATED"), Status.DEACTIVATED);
    }
}
