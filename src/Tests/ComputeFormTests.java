package Tests;

import static org.junit.jupiter.api.Assertions.*;

import ChangedClasses.Ex2Sheet;
import Interfaces.Sheet;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComputeFormTests {

    private Ex2Sheet sheet; // Mock or implementation of your Sheet interface

    @BeforeEach
    public void setup() {
        // Initialize a new sheet instance for each test
        sheet = new Ex2Sheet(10,10); // Replace with your implementation
    }

    @Test
    public void testComputeForm_NumberCell() {
        // Simple test where the cell contains a number
        sheet.set(0, 0, "42"); // A0: 42
        assertEquals("42", sheet.computeForm(0, 0)); // Expect "42" as result
    }

    @Test
    public void testComputeForm_TextCell() {
        // Test with a text cell
        sheet.set(1, 0, "Hello"); // B0: "Hello"
        assertEquals("Hello", sheet.computeForm(1, 0)); // Expect the text "Hello"
    }

    @Test
    public void testComputeForm_SimpleFormula() {
        // Test with a formula that depends on one other cell
        sheet.set(0, 0, "10");        // A0: 10
        sheet.set(0, 1, "=A0 * 2");   // B0: formula depending on A0
        assertEquals("20", sheet.computeForm(0, 1)); // Expect "20" (10 * 2)
    }

    @Test
    public void testComputeForm_MultipleDependencies() {
        // Test with a formula depending on multiple cells
        sheet.set(0, 0, "5");             // A0: 5
        sheet.set(0, 1, "=A0 * 2");       // B0: depends on A0
        sheet.set(0, 2, "=A0 + B0");      // C0: depends on A0 and B0
        assertEquals("15", sheet.computeForm(0, 2)); // Expect "15" (5 + 10)
    }

    @Test
    public void testComputeForm_CircularDependency() {
        // Test for a circular dependency
        sheet.set(0, 0, "=B0 + 1");       // A0: depends on B0
        sheet.set(0, 1, "=A0 * 2");       // B0: depends on A0

        // Ensure circular dependency results in an error
        assertEquals("ERROR: Circular dependency", sheet.computeForm(0, 0));
        assertEquals("ERROR: Circular dependency", sheet.computeForm(0, 1));
    }

    @Test
    public void testComputeForm_ErrorOnInvalidDependency() {
        // Test for invalid cell reference in formula
        sheet.set(0, 0, "=B1 + 10");      // A0: references B1, which doesn't exist
        assertEquals("ERROR: Invalid cell reference (B1)", sheet.computeForm(0, 0));
    }

    @Test
    public void testComputeForm_DependencyOnText() {
        // Test for dependency on a text cell
        sheet.set(0, 0, "Hello");         // A0: text
        sheet.set(0, 1, "=A0 + 2");       // B0: tries to compute based on A0
        assertEquals("ERROR: Invalid dependency in cell A0", sheet.computeForm(0, 1));
    }

    @Test
    public void testComputeForm_SingleLevelDependency() {
        // Test for a formula referencing a single cell directly
        sheet.set(0, 0, "10");           // A0: 10
        sheet.set(0, 1, "=A0 + 5");      // B0: depends on A0
        assertEquals("15", sheet.computeForm(0, 1)); // Expect "15" (10 + 5)
    }

    @Test
    public void testComputeForm_MultiLevelDependency() {
        // Test for a multi-level dependency chain
        sheet.set(0, 0, "5");             // A0: 5
        sheet.set(0, 1, "=A0 * 2");       // B0: depends on A0
        sheet.set(0, 2, "=B0 + 3");       // C0: depends on B0 (indirectly A0)
        assertEquals("13", sheet.computeForm(0, 2)); // Expect "13" (10 + 3)
    }

    @Test
    public void testComputeForm_EmptyCell() {
        // Test with an empty cell
        sheet.set(1, 1, ""); // B1: Empty
        assertEquals("ERROR: Undefined cell", sheet.computeForm(1, 1));
    }

    @Test
    public void testComputeForm_OutOfBounds() {
        // Test for a cell that's out of bounds
        assertEquals("ERROR: Out of bounds", sheet.computeForm(100, 100));
    }

    @Test
    public void testComputeForm_ComplexFormula() {
        // Test with a complex formula
        sheet.set(0, 0, "5");             // A0: 5
        sheet.set(0, 1, "3");             // B0: 3
        sheet.set(0, 2, "=(A0 + B0) * 4"); // C0: complex formula
        assertEquals("32", sheet.computeForm(0, 2)); // Expect "32" ((5 + 3) * 4)
    }
}