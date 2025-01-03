package Tests;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CalculateTest {

    @Test
    void testSimpleExpression() {
        // Test simple arithmetic
        String expression = "1+2";
        double expected = 3.0;
        assertEquals(expected, Cell.Calculate(expression), 0.0001);
    }

    @Test
    void testComplexExpression() {
        // Test a more complex arithmetic expression
        String expression = "1*(3/14.0*(7+19))*17";
        double expected = 94.7142857143; // Approximate value
        assertEquals(expected, Cell.Calculate(expression), 0.0001);
    }

    @Test
    void testNestedParentheses() {
        // Test expression with nested parentheses
        String expression = "((2+3)*4)/(5-3)";
        double expected = 10.0;
        assertEquals(expected, Cell.Calculate(expression), 0.0001);
    }

    @Test
    void testDivisionByZero() {
        // Test division by zero (should handle gracefully)
        String expression = "1/0";
        assertThrows(ArithmeticException.class, () -> Cell.Calculate(expression));
    }

    @Test
    void testEmptyExpression() {
        // Test empty string
        String expression = "";
        assertThrows(IllegalArgumentException.class, () -> Cell.Calculate(expression));
    }

    @Test
    void testNullExpression() {
        // Test null input
        String expression = null;
        assertThrows(NullPointerException.class, () -> Cell.Calculate(expression));
    }

}
