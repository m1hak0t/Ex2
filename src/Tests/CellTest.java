package Tests;
import ChangedClasses.CellFuntions;
import ChangedClasses.SCell;
import Interfaces.Cell;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testValidFormulas() {
        assertTrue(CellFuntions.IsForm("=1"), "Simple number formula failed");
        assertTrue(CellFuntions.IsForm("=(1+2)"), "Formula with parentheses failed");
        assertTrue(CellFuntions.IsForm("=(3*4)"), "Formula with multiplication failed");
        assertTrue(CellFuntions.IsForm("=A1+3"), "Mixed reference and number failed");
        assertTrue(CellFuntions.IsForm("=(A1+A2)*2"), "Complex formula failed");
        assertTrue(CellFuntions.IsForm("=(1+(A2*3))"), "Nested formula failed");
    }

    @Test
    public void testInvalidFormulas() {
        assertFalse(CellFuntions.IsForm("Hello"), "String wrongly identified as formula");
        assertFalse(CellFuntions.IsForm("123abc"), "Invalid alphanumeric string identified as formula");
        assertFalse(CellFuntions.IsForm("123"), "Number without '=' wrongly identified as formula");
        assertFalse(CellFuntions.IsForm("1.23"), "Decimal without '=' wrongly identified as formula");
        assertFalse(CellFuntions.IsForm("=1++2"), "Invalid formula syntax passed");
        assertFalse(CellFuntions.IsForm("=A1**3"), "Invalid operator usage passed");
    }

    @Test
    public void testEdgeCases() {
        assertFalse(CellFuntions.IsForm(""), "Empty string wrongly identified as formula");
        assertFalse(CellFuntions.IsForm(null), "Null input caused an exception or failed");
        assertFalse(CellFuntions.IsForm("=@#$%^&"), "Special characters wrongly identified as formula");
    }

    @Test
    public void testTypeAssignment() {
        // Assuming a Cell class with a constructor that uses IsForm to set type
        Cell cellFormula = new SCell("=1+2");
        assertEquals(3, cellFormula.getType(), "Valid formula not assigned type 3");

        Cell cellText = new SCell("Hello");
        assertEquals(1, cellText.getType(), "Text wrongly assigned type other than 0");

        Cell cellInvalid = new SCell("123abc");
        assertEquals(1, cellInvalid.getType(), "Invalid string wrongly assigned type other than 0");

        Cell cellNumber = new SCell("123");
        assertEquals(2, cellNumber.getType(), "Number without '=' wrongly assigned type");
    }
}
