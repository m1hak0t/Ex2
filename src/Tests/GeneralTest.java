package Tests;

import ChangedClasses.CellEntry;
import Interfaces.Cell;

public class GeneralTest {
    public static void main(String[] args) {
        CellEntry ass = new CellEntry("Z3");
        System.out.println(ass.toCords());
    }
}