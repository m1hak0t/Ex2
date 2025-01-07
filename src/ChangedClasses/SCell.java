package ChangedClasses;// Add your documentation below:

import Interfaces.Cell;


//Types list for scell
//String = 1
//Number = 2
//Formula = 3
public class SCell implements Cell {
    private String line;
    private int type;
    public int depth;
    public boolean dependant = false;
    public boolean calculatable;

    public SCell(String s) {
        setData(s);
    }


    @Override
    public int getOrder() {
        return this.depth;
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
    public void setData(String s) {
        line = s;

        //If the cell is a string - set type string
        if (CellFuntions.IsText(line)) {
            this.type = 1;
        } else {
            this.type = 1;
        }
        //If the cell is a number - set type number
        if (CellFuntions.IsNumber(line)) {
            this.type = 2;
        }
        //If the cell is a formula - set type formula
        if (CellFuntions.IsForm(line)) {
            this.type = 3;
        } else {
        }
    }
    @Override
    public String getData() {
        return line;
    }

    @Override
    public int getType() {
        return type;
    }

    @Override
    public void setType(int t) {
        type = t;
    }

    @Override
    public void setOrder(int d) {
        this.depth = d;

    }
}
