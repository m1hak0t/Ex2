package Tests;

import ChangedClasses.CellFuntions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculateTest {

    @Test
    void testSimpleExpression() {
        // Test simple arithmetic
        String expression = "1+2";
        String expected = "3.0";
        assertEquals(expected, CellFuntions.Calculate(expression));
    }

    @Test
    void testDivisionByZero() {
        // Test division by zero (should handle gracefully)
        String expression = "1/0";
        assertThrows(ArithmeticException.class, () -> CellFuntions.Calculate(expression));
    }

    @Test
    void testEmptyExpression() {
        // Test empty string
        String expression = "";
        assertThrows(IllegalArgumentException.class, () -> CellFuntions.Calculate(expression));
    }

    @Test
    void testNullExpression() {
        // Test null input
        String expression = null;
        assertThrows(NullPointerException.class, () -> CellFuntions.Calculate(expression));
    }

}
