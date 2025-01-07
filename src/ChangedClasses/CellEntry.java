package ChangedClasses;// Add your documentation below:
import Interfaces.Index2D;
import UnchangedClasses.Ex2Utils;

import java.util.ArrayList;
import java.util.logging.Logger;


//Orientational class that creates an object that represents a cell
//Cell can be called by x,y or by a string value, for example B3
public class CellEntry implements Index2D {
    private String[][] grid = new String[Ex2Utils.WIDTH][Ex2Utils.HEIGHT];
    private int _x ;
    private int _y ;
    private String stringindex;

    public CellEntry(int x, int y) {
        //Grid init in case params xy
        for (int i=0; i< Ex2Utils.WIDTH;i ++) {
            for (int j = 0; j < Ex2Utils.HEIGHT; j++) {
                char letter = (char) (i + 65);
                this.grid[i][j] = String.valueOf((letter)) + j;
            }
        }
        this._x = x;
        this._y = y;
        stringindex = this.toString();

    }
    //grid init in case params string
    public CellEntry(String cellName) {
        cellName = cellName.toUpperCase();
        this.stringindex = cellName;
        if (isValid()) {
            for (int i = 0; i < Ex2Utils.WIDTH; i++) {
                for (int j = 0; j < Ex2Utils.HEIGHT; j++) {
                    char letter = (char) (i + 65);
                    this.grid[i][j] = String.valueOf((letter)) + j;
                }
            }
            ArrayList<Integer> result = toCords();
            //System.out.println(result);
            this._x = result.get(0);
            this._y = result.get(1);
        }else
            throw new IllegalArgumentException("Illigal coords");
    }

    @Override
    public boolean isValid() {
        return stringindex.matches("^[A-Za-z][0-9]{1,2}$");
    }

    @Override
    public String toString() {
        if (stringindex!=null) {
            return stringindex;
        }
        for (int x=0; x< Ex2Utils.WIDTH;x ++) {
            for (int y=0;y<Ex2Utils.HEIGHT; y++) {
                char letter = (char)(x+65);
                this.grid[x][y] = String.valueOf((letter))+y;
            }

        }
        return grid[_x][_y];
    }

    public ArrayList<Integer> toCords() {
            ArrayList<Integer> result = new ArrayList<>();
            int x = (int)stringindex.charAt(0) - 65;
            int y = Integer.parseInt(stringindex.substring(1));
            result.add(x);
            result.add(y);
            return result;
    }

    @Override
    public int getX() {return this._x;}

    @Override
    public int getY() {return this._y;}
}


