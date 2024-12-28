import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    public static void main(String[] args) {
        System.out.println(Cell.IsForm("=123"));
    }

    @Test
    public void testIsNumberValidNumbers() {
        assertTrue(Cell.isNumber("123"));       // Integer
        assertTrue(Cell.isNumber("123.45"));    // Decimal
        assertTrue(Cell.isNumber("-123.45"));   // Negative number
    }

    @Test
    public void testIsNumberInvalidNumbers() {
        assertFalse(Cell.isNumber("abc"));      // Non-numeric
        assertFalse(Cell.isNumber("12a"));      // Mixed
        assertFalse(Cell.isNumber(null));       // Null input
        assertFalse(Cell.isNumber(""));         // Empty string
    }

    @Test
    public void testIsTestValidStrings() {
        assertTrue(Cell.IsText("123"));         // Integer
        assertTrue(Cell.IsText("123.45"));      // Decimal
    }

    @Test
    public void testIsTestInvalidStrings() {
        assertFalse(Cell.IsText("abc"));        // Non-numeric
        assertFalse(Cell.IsText("12a"));        // Mixed
        assertFalse(Cell.IsText(""));           // Empty string
        assertFalse(Cell.IsText(null));         // Null input (throws exception if not handled)
    }

    @Test
    public void testIsFormValidStrings() {
        assertTrue(Cell.IsForm("="));           // Starts with '='
        assertTrue(Cell.IsForm("=123"));        // Formula-like string
    }

    @Test
    public void testIsFormInvalidStrings() {
        assertFalse(Cell.IsForm("abc"));        // No '=' at start
        assertFalse(Cell.IsForm(""));           // Empty string
        assertFalse(Cell.IsForm(null));         // Null input (throws exception if not handled)
    }
}