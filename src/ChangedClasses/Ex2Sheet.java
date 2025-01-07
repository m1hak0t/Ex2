package ChangedClasses;

import Interfaces.Cell;
import Interfaces.Sheet;
import UnchangedClasses.Ex2Utils;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
// Add your documentation below:

/// TODO:1
/// To finish the Scell class
/// TODO:2 - The main idea of the system
///
/// <--->
/// The function depth created a 2d array of cells so its size is WIDTHxLENGTH and gives every cell a specific value of -1
/// The function eval() starts to loop through the depth 2d array and do the following:
/// If the cell does not depend on eny other cell - calculate it, if it depends on other cells -> check whether all of them calculatable
/// right now (depChecker()) and if yes -> calculate all of them and if not - wait to the next iteration. After every iteration we increase
/// the value of every uncalculated cell by 1 and check whether any changes are made during the calculation. If the depths of some set of sells
/// grows but nothing changed after the iteration -> these 2 cells have an infinite loop.
/// </--->
///
/// TODO:3 - Write a depChecker
///
/// TODO:4
/// Write depth algorithm, just go through the sheet and check whether the cell is calculatable, if yes - calculate.
/// Do this loop and make sure every iteration something changed, if there are uncalculated cells and nothing chenges - this is a circle loop

public class Ex2Sheet implements Sheet {
    public Cell[][] table;
    // Add your code here

    // ///////////////////
    public Ex2Sheet(int x, int y) {
        table = new SCell[x][y];
        for(int i=0;i<x;i=i+1) {
            for(int j=0;j<y;j=j+1) {
                table[i][j] = new SCell("");
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

        Cell c = get(x,y);
        if(c!=null) {ans = c.toString();}

        /////////////////////
        return ans;
    }

    @Override
    public Cell get(int x, int y) {
        return table[x][y];
    }

    @Override
    public Cell get(String cords) {
        Cell ans = null;
        // Add your code here

        /////////////////////
        return ans;
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
        Cell c = new SCell(s);
        table[x][y] = c;
        // Add your code here

        /////////////////////
    }
    @Override
    public void eval() {
        //int[][] dd = depth();
        int [][] ans = new int[width()][height()];
        for (int i=0; i<width(); i++) {
            for (int j=0; j<height();j++) {
                String data = table[i][j].getData();
                table[i][j].setData(String.valueOf(CellFuntions.Calculate(data)));
            }
        }

    }

    @Override
    public boolean isIn(int xx, int yy) {
        boolean ans = xx<=width() && yy<=height();
        return ans;
    }

    @Override
    public int[][] depth() {
        int[][] ans = new int[width()][height()];
        //Set all the values to -1
        //Go through the created 2d array and check whether the cell in the ex2sheet itself is dependent
        return ans;
    }

    @Override
    public void load(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }

    @Override
    public void save(String fileName) throws IOException {
        // Add your code here

        /////////////////////
    }


    /// The function that we will use in a future to define what will happen in one particular cell
    @Override
    public String eval(int x, int y) {
        String data = null;
        if(get(x,y)!=null)
        {data = get(x,y).toString();}
        //What should be done
        //depCheck the string
        //If there is an exception -> return ERR.Form
        //If there is no exception -> return Calculated form
        try {
            data = depChecker(data);
            return data;

        } catch (IllegalArgumentException e) {
            //In case something gave an invalid argument exception - return an error
            return Ex2Utils.ERR_FORM;
        }
    }


    ///Takes an expression as a string and checks whether it's dependencies are instantly calculatable
    ///If no - returns Null
    ///If yes - returns a string with the expression already calculated inside (recursive call)
    /// If the dependencies are uncalculatable types (Strings, Errors) throws an argument exception
    public String depChecker(String expression) {

        int count = 0;
        //RegEx to find coordinates like A5
        Pattern pattern = Pattern.compile("[A-Z]+[0-9]+");
        Matcher matcher = pattern.matcher(expression);
        //Now we will update all the expressions we have found and also count them!

        //While loop until there is no matches left
        if (matcher.find()) {
            //Initialise new expression to change
            StringBuilder newexpression = new StringBuilder(expression);
            String coords = matcher.group();
            //Calculate the expression in the desired cell
            CellEntry cell = new CellEntry(coords);
            //Get the coordinates of the current cell
            int x = cell.getX();
            int y = cell.getY();
            if (!isIn(x,y)) {
                throw new IllegalArgumentException("Cell " + cell.toString() + "is out of range");
            }
            //Get the string that is located there
            Cell currentcell = this.table[x][y];
            System.out.println(currentcell.getType());
            System.out.println(currentcell.getData());
            //If there is an error inside = throw an exception of illigal argument
            if (currentcell.getData().equals(Ex2Utils.ERR_FORM) || currentcell.getData().equals(Ex2Utils.ERR_CYCLE)) {
                throw new IllegalArgumentException("One of the dependencies is an error");
            }
            //Only if the datatype is calculatable we want to calculate the cell
            if (currentcell.getType() == 2 || currentcell.getType() == 3) {
                //First and last index of the found data:
                int first = matcher.start();
                int last = matcher.end();
                //Caclulated form can give an error in case we are dividing by 0 - catch that
                String calculated = null;
                try {
                    //Get the datastring from the dependant cell
                    String current_expression = currentcell.getData();
                    String calculated_expression = String.valueOf(CellFuntions.ComputeForm(current_expression));
                    calculated = calculated_expression;
                } catch (Exception e) {
                    //Set en errormessage in the uncalculatable cell in case its uncalculatable instantly
                    return null;
                }
                newexpression.replace(first, last, calculated);
                //Set the expression string== to newexpression that we've changes
                expression = newexpression.toString();
                //Recursive call of the function with the new data
                return depChecker(expression);
            } else {
                throw new IllegalArgumentException("The dependant cell" + cell.toString()+ " is uncalculatable");
            }
        }
        return CellFuntions.Calculate(expression);
    }
}
