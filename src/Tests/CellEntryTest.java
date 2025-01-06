package Tests;
import static org.junit.jupiter.api.Assertions.*;

import ChangedClasses.CellEntry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class CellEntryTest {

    private CellEntry cellEntryXY;
    private CellEntry cellEntryString;

    @BeforeEach
    public void setUp() {
        // Initialize the CellEntry objects with different constructors
        cellEntryXY = new CellEntry(2, 3); // Assuming valid grid coordinates
        cellEntryString = new CellEntry("C3"); // Assuming the string corresponds to a valid cell
    }

    @Test
    public void testConstructorWithXY() {
        // Verify that the object is correctly initialized with x and y coordinates
        assertEquals(2, cellEntryXY.getX());
        assertEquals(3, cellEntryXY.getY());
    }

    @Test
    public void testConstructorWithString() {
        // Verify that the object is correctly initialized with a string cell name
        assertEquals("C3", cellEntryString.toString());
        assertEquals(3, cellEntryString.getX());
        assertEquals(3, cellEntryString.getY());
    }

    @Test
    public void testIsValidWithValidString() {
        // Test if the isValid method correctly identifies a valid cell
        assertTrue(cellEntryString.isValid());
    }


    @Test
    public void testToCords() {
        // Test if the toCords method correctly converts the string index into coordinates
        ArrayList<Integer> coords = cellEntryString.toCords();
        assertEquals(3, coords.get(0)); // x = 'C' -> 3
        assertEquals(3, coords.get(1)); // y = 3
    }

    @Test
    public void testToStringWithCoordinates() {
        // Test the toString method for a valid coordinate-based entry
        assertEquals("C3", cellEntryString.toString());
    }

}

