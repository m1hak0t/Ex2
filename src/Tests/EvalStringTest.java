package Tests;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import UnchangedClasses.Ex2Utils;
import org.junit.jupiter.api.Test;

import ChangedClasses.Ex2Sheet;

public class EvalStringTest {

    @Test
    public void testEvalDirectDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);

        // Test 1: Simple Calculation Without Dependencies
        sheet.set(0, 0, "5");
        assertEquals("5.0", sheet.eval(0, 0));

        // Test 2: Direct Dependency
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "=A0+3");
        assertEquals("8.0", sheet.eval(1, 0));

        // Test 3: Chain Dependency (Unsupported)
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "=A0+3");
        sheet.set(2, 0, "=B0+2");
        assertNull(sheet.eval(2, 0)); // Chain dependencies are not resolved

        // Test 5: Invalid Dependency (Non-Existent Cell)
        sheet.set(8, 0, "=Z9+3");
        assertEquals(Ex2Utils.ERR_FORM, sheet.eval(8, 0));

        // Test 6: Division by Zero
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "0");
        sheet.set(2, 0, "=A0/B0");
        assertEquals(Ex2Utils.ERR_FORM, sheet.eval(2, 0));

        // Test 7: Unresolvable Dependency
        sheet.set(0, 0, "=B0+3");
        sheet.set(1, 0, "ERR_FORM!");
        assertEquals(Ex2Utils.ERR_FORM, sheet.eval(0, 0));
    }
    @Test
    public void testEvalDirectDependencies1() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);
        //Test 8: Random string
        sheet.set(0, 0, "=B0+3");
        sheet.set(1, 0, "asdgsdgds)g");
        assertEquals(Ex2Utils.ERR_FORM, sheet.eval(0, 0));
    }

}
