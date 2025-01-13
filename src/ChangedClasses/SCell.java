package ChangedClasses;// Add your documentation below:

import Interfaces.Cell;
import UnchangedClasses.Ex2Utils;

import java.util.Optional;


//Types list for scell
//String = 1
//Number = 2
//Formula = 3
public class SCell implements Cell {
    /**
     * The data contained in the cell as a string.
     */
    private String line;

    /**
     * The type of the cell (1 = String, 2 = Number, 3 = Formula).
     */
    private int type;

    /**
     * The depth or order of precedence for the cell.
     */
    public int depth;

    /**
     * The formula of the cell, if the cell type is formula (type 3).
     */
    public String formula;


    /**
     * Retrieves the formula of the cell.
     *
     * @return The formula as a string, or null if no formula exists.
     */
    public String getFormula() {
        return formula;
    }
    /**
     * Constructs an SCell instance and initializes its data.
     *
     * @param s The initial data for the cell.
     */
    public SCell(String s) {
        setData(s);
    }


    @Override
    /**
     * Gets the depth or order of precedence of the cell.
     *
     * @return The depth of the cell.
     */
    public int getOrder() {
        return this.depth;
    }

    //@Override
    @Override
    /**
     * Converts the cell to a string representation.
     *
     * @return The cell contents as a string.
     */
    public String toString() {
        return line;
    }

    @Override
    /**
     * Sets the data for the cell and determines its type (String, Number, or Formula).
     *
     * @param s The data to set for the cell.
     */
    public void setData(String s) {
        line = s;
        if (this.formula!= null)
            System.out.println(formula);
        //If the cell is a string - set type string
        if (CellFuntions.IsText(line)) {
            this.type = Ex2Utils.TEXT;
        } else {
            this.type = Ex2Utils.TEXT;
        }
        //If the cell is a number - set type number
        if (CellFuntions.IsNumber(line)) {
            this.type = Ex2Utils.NUMBER;
        }
        //If the cell is a formula - set type formula
        if (CellFuntions.IsForm(line)) {
            this.formula = this.line; ///Critical line!
            this.type = Ex2Utils.FORM;
        } else {
        }
    }
    @Override
    /**
     * Retrieves the data of the cell.
     *
     * @return The data of the cell as a string.
     */
    public String getData() {
        return line;
    }

    @Override
    /**
     * Gets the type of the cell (1 = String, 2 = Number, 3 = Formula).
     *
     * @return The type of the cell.
     */
    public int getType() {
        return type;
    }

    @Override
    /**
     * Sets the type of the cell.
     *
     * @param t The type to set (1 = String, 2 = Number, 3 = Formula).
     */
    public void setType(int t) {
        type = t;
    }

    @Override
    /**
     * Sets the order (depth) for the cell.
     *
     * @param d The depth to set.
     */
    public void setOrder(int d) {
        this.depth = d;

    }
}
