package Tests;

import static org.junit.jupiter.api.Assertions.*;

import ChangedClasses.Ex2Sheet;
import org.junit.jupiter.api.Test;
import java.util.Set;

class ParseDependenciesTest {

    @Test
    public void testSingleDependency() {
        Ex2Sheet sheet = new Ex2Sheet();
        String formula = "A1";

        Set<String> dependencies = sheet.parseDependencies(formula);

        assertEquals(1, dependencies.size());
        assertTrue(dependencies.contains("A1"));
    }

    @Test
    public void testMultipleDependencies() {
        Ex2Sheet sheet = new Ex2Sheet();
        String formula = "a1+B2-C3";

        Set<String> dependencies = sheet.parseDependencies(formula);

        assertEquals(3, dependencies.size());
        assertTrue(dependencies.contains("a1"));
        assertTrue(dependencies.contains("B2"));
        assertTrue(dependencies.contains("C3"));
    }

    @Test
    public void testNoDependencies() {
        Ex2Sheet sheet = new Ex2Sheet();
        String formula = "5+10-3";

        Set<String> dependencies = sheet.parseDependencies(formula);

        assertTrue(dependencies.isEmpty());
    }

    @Test
    public void testEdgeCaseSingleLetter() {
        Ex2Sheet sheet = new Ex2Sheet();
        String formula = "A";

        Set<String> dependencies = sheet.parseDependencies(formula);

        assertTrue(dependencies.isEmpty()); // "A" is not a valid cell reference like "A1"
    }

    @Test
    public void testInvalidReferences() {
        Ex2Sheet sheet = new Ex2Sheet();
        String formula = "A1+B+12+D10";

        Set<String> dependencies = sheet.parseDependencies(formula);

        assertEquals(2, dependencies.size());
        assertTrue(dependencies.contains("A1"));
        assertTrue(dependencies.contains("D10"));
        assertFalse(dependencies.contains("B"));  // "B" is not valid
    }

    @Test
    public void testComplexFormula() {
        Ex2Sheet sheet = new Ex2Sheet();
        String formula = "A1*B2+C3-D4";

        Set<String> dependencies = sheet.parseDependencies(formula);

        assertEquals(4, dependencies.size());
        assertTrue(dependencies.contains("A1"));
        assertTrue(dependencies.contains("B2"));
        assertTrue(dependencies.contains("C3"));
        assertTrue(dependencies.contains("D4"));
    }

    @Test
    public void testEmptyString() {
        Ex2Sheet sheet = new Ex2Sheet();
        String formula = "";

        Set<String> dependencies = sheet.parseDependencies(formula);

        assertTrue(dependencies.isEmpty());
    }


}