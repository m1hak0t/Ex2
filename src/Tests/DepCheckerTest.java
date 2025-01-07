package Tests;

import ChangedClasses.Ex2Sheet;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DepCheckerTest {

    @Test
    void testDepCheckerWithNoDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5"); // A1 = 5

        String result = sheet.depChecker("5");
        assertEquals("5.0", result, "Expression without dependencies should return itself.");
    }

    @Test
    void testDepCheckerWithResolvedDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");   // A0 = 5
        sheet.set(0, 1, "10");  // A1 = 10

        String result = sheet.depChecker("=A0+A1");
        assertEquals("15.0", result, "Expression with fully resolved dependencies should return the computed result.");
    }


    @Test
    void testDepCheckerWithNestedUnresolvedDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "=A1*2"); // A0
        sheet.set(0, 1, "=A0+5"); // A1

        String result = sheet.depChecker("=A1");
        assertNull(result, "Expression with nested unresolved dependencies should return null.");
    }


    @Test
    void testDepCheckerWithMixedResolvedAndUnresolvedDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(3, 3);
        sheet.set(0, 0, "5");     // A1 = 5
        sheet.set(0, 1, "=A2+3"); // B1 depends on A2, which is unresolved

        String result = sheet.depChecker("=A1+B1");
        assertNull(result, "Expression with a mix of resolved and unresolved dependencies should return null.");
    }

    @Test
    void testDepCheckerWithAllResolvedDependencies() {
        Ex2Sheet sheet = new Ex2Sheet(10, 10);
        sheet.set(0, 5, "5");   // A0 = 5
        sheet.set(0, 6, "3");   // A1 = 3
        sheet.set(0, 7, "2");   // A2 = 2

        String result = sheet.depChecker("=A5+A6*A7");
        assertEquals("11.0", result, "Expression with all resolved dependencies should return the computed result.");
    }

    @Test
    public void testDepChecker() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);

        // Test 1: No Dependencies
        sheet.set(0, 0, "5");
        assertEquals("5.0", sheet.depChecker("5"));
    }
    @Test
    public void testDepChecker2() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);
        // Test 2: Single Dependency
        sheet.set(0, 0, "5");
        assertEquals("8.0", sheet.depChecker("=A0+3"));
    }
    @Test
    public void testDepChecker3() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);
        // Test 3: Multiple Dependencies
        sheet.set(0, 0, "5");
        sheet.set(1, 0, "10");
        assertEquals("15.0", sheet.depChecker("=A0+B0"));
    }

    @Test
    public void testDepChecker5() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);
        // Test 5: Circular Dependency
        sheet.set(0, 0, "=B0+1");
        sheet.set(1, 0, "=A0+2");
        assertNull(sheet.depChecker("=A0"));

    }
    @Test
    public void testDepChecker7() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);
        // Test 7: Dependency with Unresolvable Cell
        sheet.set(0, 0, "=B0+3");
        sheet.set(1, 0, "#ERR");
        assertNull(sheet.depChecker("=A0"));
    }


    @Test
    public void testDepChecker10() {
        Ex2Sheet sheet = new Ex2Sheet(9, 17);
        // Test 10: Dependency Loop with Valid Calculation Elsewhere
        sheet.set(0, 0, "=B0+1");
        sheet.set(1, 0, "=A0-1");
        sheet.set(2, 0, "10");
        assertEquals("12.0", sheet.depChecker("=C0+2"));
    }
}

