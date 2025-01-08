package Tests;
/*
import static org.junit.jupiter.api.Assertions.*;

import ChangedClasses.Ex2Sheet;
import Interfaces.Cell;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetStringTest {

    private Ex2Sheet sheet;

    @BeforeEach
    public void setup() {
        // Initialize the sheet with a 3x3 grid for testing (example size)
        sheet = new Ex2Sheet(3, 3);

        // Populate the grid with sample Cells
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                sheet.set(x, y,"Cell[" + x + "," + y + "]");
            }
        }
    }

    @Test
    public void testValidCoordinates() {
        // Test valid coordinates (A0, B1, C2 assuming A=0, B=1, C=2)
        assertEquals("Cell[0,0]", sheet.get("A0").toString());
        assertEquals("Cell[1,1]", sheet.get("B1").toString());
        assertEquals("Cell[2,2]", sheet.get("C2").toString());
    }

    @Test
    public void testOutOfBoundsCoordinates() {
        // Test out-of-bounds coordinates (should throw an exception)
        assertThrows(IndexOutOfBoundsException.class, () -> sheet.get("D3")); // Outside grid boundaries
        assertThrows(IndexOutOfBoundsException.class, () -> sheet.get("Z0")); // Invalid row
        assertThrows(IndexOutOfBoundsException.class, () -> sheet.get("-1")); // Invalid input
    }

    @Test
    public void testInvalidCoordinateString() {
        // Test invalid coordinate formats (should throw an exception from CellEntry)
        assertThrows(IllegalArgumentException.class, () -> sheet.get(""));      // Empty string
        assertThrows(IllegalArgumentException.class, () -> sheet.get("A"));     // Missing number
        assertThrows(IllegalArgumentException.class, () -> sheet.get("1A"));    // Invalid formatting
        assertThrows(IllegalArgumentException.class, () -> sheet.get("A-1"));   // Negative number
        assertThrows(IllegalArgumentException.class, () -> sheet.get(null));    // Null input
    }

    @Test
    public void testEdgeCaseCoordinates() {
        // Test edge case coordinates at the boundaries of the grid
        assertEquals("Cell[0,0]", sheet.get("A0").toString()); // Top-left corner
        assertEquals("Cell[2,2]", sheet.get("C2").toString()); // Bottom-right corner
    }
}
/*
 */