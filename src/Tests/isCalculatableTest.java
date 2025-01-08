package Tests;
import static org.junit.jupiter.api.Assertions.*;

import static org.junit.jupiter.api.Assertions.*;

import ChangedClasses.Ex2Sheet;
import UnchangedClasses.Ex2Utils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class isCalculatableTest {

    private Ex2Sheet sheet;

    @BeforeEach
    public void setUp() {
        // Initialize the sheet with a 3x3 grid
        sheet = new Ex2Sheet(5, 5);

        // Set cell values
        sheet.set(0, 0, "5");             // A0: Literal value
        sheet.set(0, 1, "=A0+3");        // A1: Depends on A0
        sheet.set(1, 1, "=A1-2");        // B1: Depends on A1
        sheet.set(2, 0, Ex2Utils.EMPTY_CELL);              // C0: Empty cell
        sheet.set(2, 2, "=B1*2");        // C2: Depends on B1
    }

    @Test
    public void testCellWithNoData() {
        // Empty cell (C0) should not be calculatable
        assertFalse(sheet.isCalculatable("C0"), "Cell C0 has no value or dependencies, so it should not be calculatable.");
    }

    @Test
    public void testLiteralCell() {
        // Literal value cell (A0) is calculatable
        assertTrue(sheet.isCalculatable("A0"), "Cell A0 is a literal value and should be calculatable.");
    }

    @Test
    public void testCellWithResolvedDependency() {
        // A1 depends on A0, and A0 is already computed
        assertTrue(sheet.isCalculatable("A1"), "Cell A1 should be calculatable as A0 is already computed.");
    }

    @Test
    public void testCellWithUnresolvedDependency() {
        // B1 depends on A1, and A1 should already be calculatable
        assertTrue(sheet.isCalculatable("B1"), "Cell B1 should be calculatable as its dependency (A1) is calculatable.");
    }


    @Test
    public void testCellWithDependencyChain() {
        // C2 depends on B1, which in turn depends on A1 -> A0
        // Since A0, A1, and B1 are calculatable, C2 should also be calculatable
        assertTrue(sheet.isCalculatable("C2"), "Cell C2 should be calculatable as its dependency chain is resolved.");
    }
}