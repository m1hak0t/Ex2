package ChangedClasses;// Add your documentation below:

import Interfaces.Cell;

public class SCell implements Cell {
    private String line;
    private int type;
    public boolean dependant = false;
    public int depends_on;
    public boolean isText;
    public boolean isNumber;
    public boolean isFormula;
    public boolean calculatable;

    public SCell(String s) {

        setData(s);
    }


    @Override
    public int getOrder() {
        // Add your code here

        return 0;
        // ///////////////////
    }

    //@Override
    @Override
    public String toString() {
        return getData();
    }

    @Override
    public void setData(String s) {
        line = s;
        //If the cell is a number - set type number
        if (CellFuntions.isNumber(line)) {
            this.isNumber = true;
        } else {this.isNumber= false;}
        //If the cell is a string - set type string
        if (CellFuntions.IsText(line)) {
            this.isText = true;
        } else {this.isText= false;}
        //If the cell is a formula - set type formula
        if (CellFuntions.IsForm(line)) {
            this.isFormula = true;
        } else {this.isFormula= false;}
        //Check whether it's calculatable
        if ((this.isFormula || this.isNumber) && !this.dependant) {
            this.calculatable = true;
        } else {
            this.calculatable = false;}
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
    public void setOrder(int t) {
        // Add your code here

    }
}
