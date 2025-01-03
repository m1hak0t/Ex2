import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class LooperTest {
    @Test
    public void testBasicAddition() {
        String input = "2+3";
        assertEquals(5, Cell.Calculate(input));
    }

    @Test
    public void testBasicSubtraction() {
        String input = "5-3";
        assertEquals(2, Cell.Calculate(input));
    }

    @Test
    public void testBasicMultiplication() {
        String input = "4*2";
       assertEquals(8, Cell.Calculate(input));
    }

    @Test
    public void testBasicDivision() {
        String input = "8/2";
        assertEquals(4, Cell.Calculate(input));
    }

    @Test
    public void testComplexExpression() {
        String input = "2+3*4-6/2";
        assertEquals(11, Cell.Calculate(input));
    }

    @Test
    public void testWithRationalNumbers() {
        String input = "2.5*4";
        assertEquals(10, Cell.Calculate(input));
    }

    @Test
    public void testDivisionByRationalNumber() {
        String input = "10/2.5";
        assertEquals(4, Cell.Calculate(input));
    }

    @Test
    public void testNegativeNumbers() {
        String input = "-3+7";
        assertEquals(4, Cell.Calculate(input));
    }



    @Test
    public void testEdgeCaseSingleNumber() {
        String input = "42";
        assertEquals(42, Cell.Calculate(input));
    }

    @Test
    public void testDecimalPrecision() {
        String input = "10/3";
        assertEquals(3.3333, Cell.Calculate(input), 0.0001);
    }

    @Test
    public void testOperatorPrecedence() {
        String input = "2+3*4";
        assertEquals(14, Cell.Calculate(input));
    }
}

