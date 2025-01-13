package ChangedClasses;

import Interfaces.Cell;
import Interfaces.Sheet;
import UnchangedClasses.Ex2Utils;

import java.io.*;
import java.util.HashSet;
import java.util.Set;

import static ChangedClasses.CellFuntions.*;
import static java.lang.reflect.Array.set;

/**
 * The Ex2Sheet class implements the Sheet interface, providing
 * a two-dimensional table structure to represent a spreadsheet.
 * Each cell in the table is stored as an instance of the SCell class.
 *
 * <p>This class supports basic spreadsheet operations such as:
 * - Accessing and modifying cell values.
 * - Evaluating cell formulas.
 * - Loading and saving the spreadsheet state to/from a file.
 * - Managing cell dependencies for formulas.
 *
 * <p>Key Features:
 * - Variable-sized table representation.
 * - Formula parsing with dependency checks.
 * - Depth analysis for circular dependencies.
 * - Utility methods for conversions and debugging.
 */

public class Ex2Sheet implements Sheet {


    public SCell[][] table;


    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for (int i = 0; i < x; i = i + 1) {
            for (int j = 0; j < y; j = j + 1) {
                table[i][j] = new SCell(Ex2Utils.EMPTY_CELL);
            }
        }

        eval();
    }

    public Ex2Sheet() {
        this(Ex2Utils.WIDTH, Ex2Utils.HEIGHT);
    }

    @Override
    public String value(int x, int y) {
        String ans = Ex2Utils.EMPTY_CELL;
        // Add your code here

        Cell c = get(x, y);
        if (c != null) {
            ans = c.toString();
        }

        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        CellEntry cell = new CellEntry(cords);
        int _x = cell.getX();
        int _y = cell.getY();
        return table[_x][_y];
    }

    @Override
    public int width() {
        return table.length;
    }

    @Override
    public int height() {
        return table[0].length;
    }

    @Override
    public void set(int x, int y, String s) {
        table[x][y].setData(s);
        eval();
    }

    @Override
    public void eval() {

        int[][] dd = depth();
        //Iterate until the biggest depth comes
        for (int q = 0; q < findMaxInteger(dd) + 1; q = q + 1) {
            //Iterate through depth and calculate
            for (int x = 0; x < width(); x = x + 1) {
                for (int y = 0; y < height(); y = y + 1) {
                    if (dd[x][y] == q) {
                        CellEntry cell = new CellEntry(x, y);
                        table[x][y].setData(eval(x, y));
                    }
                }

            }
        }
        //Itarate in case there are -1
        //Iterate through depth and calculate
        for (int x = 0; x < width(); x = x + 1) {
            for (int y = 0; y < height(); y = y + 1) {
                if (dd[x][y] == -1) {
                    CellEntry cell = new CellEntry(x, y);
                    table[x][y].setData(Ex2Utils.ERR_CYCLE);
                }
            }


        }
    }

    @Override
    public int[][] depth() {
        int[][] ans = Depth();
        return ans;
    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx >= 0 && yy >= 0;
        // Add your code here

        /////////////////////
        return ans;
    }

    public int[][] Depth() {
        int width = this.width();
        int height = this.height();

        // Initialize depth matrix and helper arrays
        int[][] depthMatrix = new int[width][height];
        boolean[][] visited = new boolean[width][height];
        boolean[][] inStack = new boolean[width][height];

        // Fill depthMatrix with -1 to indicate uncomputed cells
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                depthMatrix[i][j] = -1;
            }
        }

        // Compute depth for each cell
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (depthMatrix[i][j] == -1) { // If depth is not already computed
                    depthcheck(i, j, depthMatrix, visited, inStack);
                }
            }
        }

        return depthMatrix; // Return the completed depth matrix
    }

    public int depthcheck(int x, int y, int[][] depthMatrix, boolean[][] visited, boolean[][] inStack) {
        // Base case: Check if the cell is valid in the table
        if (!isIn(x, y)) return 0; // Cell is out of bounds, depth = 0

        // If depth is already computed, return it
        if (depthMatrix[x][y] != -1) return depthMatrix[x][y];

        // Detect circular dependency by checking if the cell is already in the call stack
        if (inStack[x][y]) {
            // Circular dependency found, mark this cell's depth as -1
            depthMatrix[x][y] = -1;
            return -1;
        }

        // Mark the cell as visited and part of the recursion stack
        visited[x][y] = true;
        inStack[x][y] = true;

        // Parse the cell's formula to get its dependencies
        Set<String> dependencies = parseDependencies(get(x, y).getData());

        int maxDependencyDepth = 0;

        // Recursive DFS on all dependencies of the current cell
        for (String dep : dependencies) {
            // Convert dependency (e.g., "A1") into coordinates (x, y)
            Cell dependentCell = get(dep);
            if (dependentCell == null) {
                // Skip if the dependent cell is invalid (e.g., out of bounds or empty)
                continue;
            }
            System.out.println(dependentCell.toString());
            CellEntry cell = new CellEntry(dep);
            int depX = cell.getX(); // X-coordinate of the dependency
            int depY = cell.getY(); // Y-coordinate of the dependency

            int dependentDepth = depthcheck(depX, depY, depthMatrix, visited, inStack);

            // If a circular dependency is encountered, propagate the failure (-1)
            if (dependentDepth == -1) {
                depthMatrix[x][y] = -1;
                inStack[x][y] = false; // Remove from the recursion stack before returning
                return -1;
            }

            // Update the maximum dependency depth
            maxDependencyDepth = Math.max(maxDependencyDepth, dependentDepth);
        }

        // The depth of the current cell is 1 + max of its dependencies
        depthMatrix[x][y] = maxDependencyDepth + 1;

        // Remove the cell from the recursion stack
        inStack[x][y] = false;
        for (x = 0; x < x; x = x + 1) {
            for (y = 0; y < y; y = y + 1) {
                System.out.println(depthMatrix[x][y]);
            }
        }
        return depthMatrix[x][y];
    }

    @Override
    public void load(String fileName) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(fileName))) {
            String line;
            int rowIndex = 0;

            // Iterate over rows in the file
            while ((line = reader.readLine()) != null) {
                // Split the line into cells by commas, handling escaped characters
                String[] cells = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");

                if (table.length <= rowIndex || table[0].length < cells.length) {
                    throw new IOException("File dimensions do not match sheet dimensions.");
                }

                // Add the parsed values into the table row
                for (int colIndex = 0; colIndex < cells.length; colIndex++) {
                    String cellValue = cells[colIndex];

                    // Remove quotes if the value was escaped
                    if (cellValue.startsWith("\"") && cellValue.endsWith("\"")) {
                        cellValue = cellValue.substring(1, cellValue.length() - 1).replace("\"\"", "\"");
                    }

                    // Set data to the table
                    table[rowIndex][colIndex].setData(cellValue);
                }

                rowIndex++;
            }

            // Check if the number of lines matches the height of the table
            if (rowIndex > table.length) {
                throw new IOException("File contains more rows than sheet dimensions allow.");
            }
        }

        // Recalculate values after loading
        eval();
    }

    @Override
    public void save(String fileName) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName))) {
            // Iterate over the rows in the sheet
            for (int i = 0; i < table.length; i++) {
                StringBuilder row = new StringBuilder();

                // Iterate over the cells in each row
                for (int j = 0; j < table[i].length; j++) {
                    Cell cell = table[i][j];

                    // Use the cell's content (either formula, value, etc.)
                    String cellValue;
                    if (cell instanceof SCell) {
                        SCell scell = (SCell) cell;
                        if (scell.getFormula() != null) {
                            // Save the formula if it exists
                            cellValue = scell.getFormula();
                        } else {
                            // Save the evaluated value or the raw content
                            cellValue = scell.toString();
                        }
                    } else {
                        // General fallback for Cell objects
                        cellValue = cell.toString();
                    }

                    // Add the cell content to the row with escaping logic if needed
                    if (cellValue.contains(",") || cellValue.contains("\"")) {
                        // Escape special characters
                        cellValue = "\"" + cellValue.replace("\"", "\"\"") + "\"";
                    }

                    row.append(cellValue);

                    // Add a comma unless it's the last cell in the row
                    if (j < table[i].length - 1) {
                        row.append(",");
                    }
                }

                // Write the completed row into the file
                writer.write(row.toString());
                writer.newLine();
            }
        }
        System.out.println("Sheet successfully saved to " + fileName);
    }

    @Override
    public String eval(int x, int y) {
        String ans = null;
        if (get(x, y) != null) {
            try {
                ans = String.valueOf(computeForm(x, y));
            } catch (Exception e) {
                ans = Ex2Utils.ERR_FORM;
            }
        }
        return ans;
    }

    // Helper function to parse dependencies from a formula
    public Set<String> parseDependencies(String formula) {
        Set<String> dependencies = new HashSet<>();

        // Example parsing: Find all cell references like "A1", "B2", etc.
        // Customize this regex based on your formula syntax
        String regex = "[A-Za-z][0-9]+";
        java.util.regex.Pattern pattern = java.util.regex.Pattern.compile(regex);
        java.util.regex.Matcher matcher = pattern.matcher(formula);

        while (matcher.find()) {
            dependencies.add(matcher.group());
        }

        return dependencies;
    }

    // Method to determine if a cell is calculatable
    public boolean isCalculatable(String cords) {
        CellEntry cellEntry = new CellEntry(cords); // Parse coordinates, e.g., A1 -> (0,0)
        int x = cellEntry.getX();
        int y = cellEntry.getY();

        Cell cell = table[x][y];

        if (cell.getData().equals(Ex2Utils.EMPTY_CELL)) {
            return true;
        }

        // Parse formula to get dependencies
        Set<String> dependencies = parseDependencies(cell.getData());
        for (String dep : dependencies) {
            CellEntry dependencyEntry = new CellEntry(dep); // Parse dependency coordinates
            int depX = dependencyEntry.getX();
            int depY = dependencyEntry.getY();

            Cell dependencyCell = table[depX][depY];
            CellEntry cellentry = new CellEntry(depX, depY);
            if (parseDependencies(dependencyCell.getData()).size() != 0) {
                System.out.println("Dependency " + cellentry.toString() + " is not empty");
                // Unresolved dependency, so cell is not calculatable
                return false;
            }
        }

        // All dependencies are calculated
        return true;
    }

    // Utility to print the current state of the grid
    private void printGrid(int[][] grid) {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[i].length; j++) {
                System.out.printf("%4d", grid[i][j]); // Print each element with padding for readability
            }
            System.out.println(); // Newline after each row
        }
        System.out.println(); // Extra newline for better separation
    }

    public String computeForm(int x, int y) {
        // Check if the cell exists within the bounds of the spreadsheet
        if (!isIn(x, y)) return "ERROR: Out of bounds";


        // Retrieve the cell
        Cell cell = get(x, y);

        if (cell == null) return Ex2Utils.ERR_FORM;
        String data = cell.getData();
        if (data != "") {
            System.out.println("To evaluate :" + cell.toString());
        }
        if (table[x][y].formula != null) {
            System.out.println("Formula cell");
            data = table[x][y].formula;
        } else {
            data = cell.getData();
        }

        // Handle cases based on cell type
        if (IsNumber(data)) {
            // If the cell is a number, return it as a string
            return data;
        } else if (IsText(data)) {
            // If the cell is text, return it directly
            return data;
        } else if (IsForm(data)) {
            // If the cell is a formula:

            // Parse dependencies (e.g., "=A1 + B2" should extract A1 and B2)
            Set<String> dependencies = parseDependencies(data);

            // Resolve dependencies and replace them with their actual values
            String formulaToEvaluate = data.substring(1); // Remove the "=" at the start
            for (String dep : dependencies) {
                // Convert cell reference (e.g., "A1") to coordinates
                Cell depCell = get(dep);
                if (depCell == null) return "ERR: (" + dep + ")";
                CellEntry depEntry = new CellEntry(dep);
                int depX = depEntry.getX();
                int depY = depEntry.getY();

                // Recursively compute the value of the dependency
                String depValue = computeForm(depX, depY);

                // If the dependency is text or results in an error, propagate the error
                if (IsText(depValue) || depValue.startsWith("ERROR")) {
                    return "ERR:" + dep;
                }

                // Replace the dependency reference in the formula with its computed value
                formulaToEvaluate = formulaToEvaluate.replace(dep, depValue);
            }

            // Evaluate the formula using the calculate() method
            try {
                String result = CellFuntions.Calculate((formulaToEvaluate));
                return result;
            } catch (Exception e) {
                return "ERROR: Calculation error in cell (" + x + "," + y + ")";
            }
        }

        // Unknown type or invalid content empty cell
        return Ex2Utils.EMPTY_CELL;
    }

    public static int findMaxInteger(int[][] array) {
        // Initialize max with the smallest possible integer
        int max = Integer.MIN_VALUE;

        // Iterate over all rows
        for (int[] row : array) {
            // Iterate over all elements in the current row
            for (int value : row) {
                // Update max if the current value is greater
                if (value > max) {
                    max = value;
                }
            }
        }

        return max;
    }


}


