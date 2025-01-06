package ChangedClasses;

import Interfaces.Cell;
import Interfaces.Sheet;
import UnchangedClasses.Ex2Utils;

import java.io.IOException;
// Add your documentation below:

/// TODO:1
/// Improve all the functions in the cell like you drew in a notebook
/// TODO:2
/// Each cell has to have a list of dependencies as an object property, it's calculatable once all the depenencies are calculatable.
/// is something that is being check after the input
/// TODO:3
/// Write depth algorithm, just go through the sheet and check whether the cell is calculatable, if yes - calculate.
/// Do this loop and make sure every iteration something changed, if there are uncalculated cells and nothing chenges - this is a circle loop

public class Ex2Sheet implements Sheet {
    private Cell[][] table;
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
        boolean ans = xx>=0 && yy>=0;
        // Add your code here

        /////////////////////
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

    @Override
    public String eval(int x, int y) {
        String ans = null;
        if(get(x,y)!=null) {ans = get(x,y).toString();}
        // Add your code here

        /////////////////////
        return ans;
        }
}
