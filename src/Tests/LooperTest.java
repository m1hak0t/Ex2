package Tests;
import ChangedClasses.CellFuntions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class LooperTest {
    @Test
    public void testBasicAddition() {
        String input = "2+3";
        assertEquals("5.0", CellFuntions.Calculate(input));
    }

    @Test
    public void testBasicSubtraction() {
        String input = "5-3";
        assertEquals("2.0", CellFuntions.Calculate(input));
    }

    @Test
    public void testBasicMultiplication() {
        String input = "4*2";
       assertEquals("8.0", CellFuntions.Calculate(input));
    }

    @Test
    public void testBasicDivision() {
        String input = "8/2";
        assertEquals("4.0", CellFuntions.Calculate(input));
    }

    @Test
    public void testComplexExpression() {
        String input = "2+3*14.0-6/2";
        assertEquals("41.0", CellFuntions.Calculate(input));
    }

    @Test
    public void testWithRationalNumbers() {
        String input = "2.5*4";
        assertEquals("10.0", CellFuntions.Calculate(input));
    }

    @Test
    public void testDivisionByRationalNumber() {
        String input = "10/2.5";
        assertEquals("4.0", CellFuntions.Calculate(input));
    }

    @Test
    public void testNegativeNumbers() {
        String input = "-3+7";
        assertEquals("4.0", CellFuntions.Calculate(input));
    }



    @Test
    public void testEdgeCaseSingleNumber() {
        String input = "42";
        assertEquals("42.0", CellFuntions.Calculate(input));
    }



    @Test
    public void testOperatorPrecedence() {
        String input = "2+3*4";
        assertEquals("14.0", CellFuntions.Calculate(input));
    }
}



