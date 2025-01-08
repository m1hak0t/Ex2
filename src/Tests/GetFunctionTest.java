package Tests;

import static org.junit.jupiter.api.Assertions.*;

import ChangedClasses.Ex2Sheet;
import UnchangedClasses.Ex2Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetFunctionTest {

    private Ex2Sheet sheet;

    @BeforeEach
    public void setup() {
        // Initialize the sheet with a 3x3 grid
        sheet = new Ex2Sheet(3, 3);
        initializeCells();
    }

    /**
     * Initializes all cells in the grid.
     * Sets A0 explicitly to "0.0" as specified.
     */
    private void initializeCells() {
        for (int x = 0; x < 3; x++) {
            for (int y = 0; y < 3; y++) {
                sheet.set(x, y, Ex2Utils.EMPTY_CELL); // Initialize cells as empty by default
            }
        }
        sheet.set(0, 0, "0.0"); // Explicitly set A0 to "0.0"
    }

    @Test
    public void testGetValidCell() {
        // Test pre-initialized A0 value
        assertEquals("0.0", sheet.get(0, 0).toString()); // Check A0 explicitly

        // Set additional values for testing
        sheet.set(1, 1, "123");
        sheet.set(2, 2, "Test Value");

        // Test retrieving valid cells
        assertEquals("123", sheet.get(1, 1).toString()); // B2
        assertEquals("Test Value", sheet.get(2, 2).toString()); // C3
    }

    @Test
    public void testGetEmptyCell() {
        // Verify uninitialized cells return the empty cell value
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.get(0, 1).toString()); // A1
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.get(1, 2).toString()); // B3
    }

    @Test
    public void testGetOutOfBounds() {
        // Attempt to get values from out-of-bounds cells
        assertThrows(IndexOutOfBoundsException.class, () -> sheet.get(3, 3)); // Outside grid boundary
        assertThrows(IndexOutOfBoundsException.class, () -> sheet.get(-1, 0)); // Negative index
        assertThrows(IndexOutOfBoundsException.class, () -> sheet.get(0, -1)); // Negative index
    }

    @Test
    public void testGetAfterUpdate() {
        // Set initial value for a cell
        sheet.set(1, 1, "Initial Value");
        assertEquals("Initial Value", sheet.get(1, 1).toString()); // Verify initial value

        // Update the same cell with a new value
        sheet.set(1, 1, "Updated Value");
        assertEquals("Updated Value", sheet.get(1, 1).toString()); // Verify updated value

        // Ensure A0 is unaffected by other updates
        assertEquals("0.0", sheet.get(0, 0).toString()); // A0 remains unchanged
    }

    @Test
    public void testGetAcrossDifferentCells() {
        // Set values across various cells
        sheet.set(0, 1, "First Row Second Column"); // A1
        sheet.set(2, 0, "Last Row First Column");  // C1

        // Verify retrieving set values by their coordinates
        assertEquals("First Row Second Column", sheet.get(0, 1).toString()); // A1
        assertEquals("Last Row First Column", sheet.get(2, 0).toString());   // C1

        // A0 should still return the preset "0.0"
        assertEquals("0.0", sheet.get(0, 0).toString()); // Check A0 explicitly
    }

    @Test
    public void testGetWithNullValue() {
        // Explicitly set a cell to null
        sheet.set(1, 1, Ex2Utils.EMPTY_CELL);

        // Null cells should return the default EMPTY_CELL value
        assertEquals(Ex2Utils.EMPTY_CELL, sheet.get(1, 1).toString());
    }
}